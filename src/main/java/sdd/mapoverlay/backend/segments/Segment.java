package sdd.mapoverlay.backend.segments;

import sdd.mapoverlay.backend.points.EndPoint;
import sdd.mapoverlay.backend.points.types.PointType;

public class Segment {
    private EndPoint upperEndPoint;
    private EndPoint lowerEndPoint;

    public Segment(EndPoint upperEndPoint, EndPoint lowerEndPoint){
        this.upperEndPoint = upperEndPoint;
        this.lowerEndPoint = lowerEndPoint;
    }

    public EndPoint getUpperEndPoint(){
        return upperEndPoint;
    }

    public EndPoint getLowerEndPoint(){
        return lowerEndPoint;
    }

    public EndPoint getLeftEndPoint(){
        if (upperEndPoint.getPosition() == PointType.LEFT){
            return upperEndPoint;
        } else {
            return lowerEndPoint;
        }
    }

    public EndPoint getRightEndPoint(){
        if (upperEndPoint.getPosition() == PointType.RIGHT){
            return upperEndPoint;
        } else {
            return lowerEndPoint;
        }
    }



}
