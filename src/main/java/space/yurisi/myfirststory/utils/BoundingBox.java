package space.yurisi.myfirststory.utils;

public class BoundingBox {
    private double minX;
    private double minY;
    private double minZ;

    private double maxX;
    private double maxY;
    private double maxZ;

    public BoundingBox(double x1, double y1, double z1, double x2, double y2, double z2){
        double tmp;

        if(x2 < x1){
            tmp = x2;
            x2 = x1;
            x1 = tmp;
        }

        if(y2 < y1){
            tmp = y2;
            y2 = y1;
            y1 = tmp;
        }

        if(z2 < z1){
            tmp = z2;
            z2 = z1;
            z1 = tmp;
        }

        this.minX = x1;
        this.minY = y1;
        this.minZ = z1;
        this.maxX = x2;
        this.maxY = y2;
        this.maxZ = z2;
    }

    public boolean isOverlapping(BoundingBox other){
        return (minX <= other.maxX && maxX >= other.minX) && (minY <= other.maxY && maxY >= other.minY) && (minZ <= other.maxZ && maxZ >= other.minZ);
    }
}
