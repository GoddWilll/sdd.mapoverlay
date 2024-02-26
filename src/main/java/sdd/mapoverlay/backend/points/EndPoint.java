package sdd.mapoverlay.backend.points;

import sdd.mapoverlay.backend.points.types.PointType;

public class EndPoint extends Point{
    private PointType pointType;
    private PointType position;
    public EndPoint(double x_coord, double y_coord, PointType pointType, PointType position) {
        super(x_coord, y_coord);
        this.pointType = pointType;
        this.position = position;
    }

    public PointType getPointType(){
        return pointType;
    }

    public PointType getPosition(){
        return position;
    }
}
