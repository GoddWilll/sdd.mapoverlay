package sdd.mapoverlay.backend.points;

public abstract class Point {
    private double xCoord;
    private double yCoord;

    public Point(double xCoord, double yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public double getXCoords(){
        return xCoord;
    }

    public double getYCoords() {
        return yCoord;
    }

}
