package sdd.mapoverlay.backend.points;

public class Point {
    private double x_coord;
    private double y_coord;

    public Point(double x_coord,  double y_coord){
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }

    public double getX_coords(){
        return x_coord;
    }

    public double getY_coords() {
        return y_coord;
    }
}
