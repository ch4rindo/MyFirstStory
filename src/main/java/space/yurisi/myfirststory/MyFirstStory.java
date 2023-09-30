package space.yurisi.myfirststory;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.myfirststory.utils.BoundingBox;
import space.yurisi.myfirststory.utils.Vector3;
import space.yurisi.universecore.UniverseCoreAPI;
import space.yurisi.universecore.database.DatabaseManager;
import space.yurisi.universecore.database.models.User;
import space.yurisi.universecore.expection.UserNotFoundException;

import java.util.LinkedHashMap;

public final class MyFirstStory extends JavaPlugin implements Listener {

    private DatabaseManager databaseManager;
    private LinkedHashMap<Integer, Vector3> data = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        databaseManager = UniverseCoreAPI.getInstance().getDatabaseManager();
        databaseManager.getMoneyRepository();

        getLogger().info("プラグインを読み込んだで");
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();

        User user;
        try {
            user = databaseManager.getUserRepository().getUserFromUUID(player.getUniqueId());
        }catch(UserNotFoundException exception){
            user = databaseManager.getUserRepository().createUser(player);
        }

        event.joinMessage(Component.text(name + "が参加したわよ"));
    }

    @EventHandler
    public void onTouch(BlockBreakEvent event){
        Player player = event.getPlayer();

        if(!player.getInventory().getItemInMainHand().getType().equals(Material.WOODEN_AXE)){
            return;
        }

        Block block = event.getBlock();

        event.setCancelled(true);

        Location location = block.getLocation();
        data.put(data.size() + 1, new Vector3(location.getBlockX(), location.getBlockY(), location.getBlockZ()));

        player.sendMessage(Component.text(data.size() + "個目のデータを作成しました"));

        if(data.size() >= 4){
            BoundingBox a1 = new BoundingBox(data.get(1).x, data.get(1).y, data.get(1).z, data.get(2).x, data.get(2).y, data.get(2).z);
            BoundingBox a2 = new BoundingBox(data.get(3).x, data.get(3).y, data.get(3).z, data.get(4).x, data.get(4).y, data.get(4).z);

            if(a2.isOverlapping(a1)){
                player.sendMessage(Component.text("うんこ"));
            }else{
                player.sendMessage(Component.text("重なってない"));
            }

            data = new LinkedHashMap<>();
        }
    }
}
