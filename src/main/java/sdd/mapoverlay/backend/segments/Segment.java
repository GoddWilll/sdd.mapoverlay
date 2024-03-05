package sdd.mapoverlay.backend.segments;

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.points.types.Position;


public class Segment {
    private EventPoint upperEventPoint;
    private EventPoint lowerEventPoint;

    public Segment(EventPoint upperEventPoint, EventPoint lowerEventPoint){
        this.upperEventPoint = upperEventPoint;
        this.lowerEventPoint = lowerEventPoint;
    }

    public EventPoint getUpperEndPoint(){
        return upperEventPoint;
    }

    public EventPoint getLowerEndPoint(){
        return lowerEventPoint;
    }

    public EventPoint getLeftEndPoint(){
        if (upperEventPoint.getPosition() == Position.LEFT){
            return upperEventPoint;
        } else {
            return lowerEventPoint;
        }
    }

    public EventPoint getRightEndPoint(){
        if (upperEventPoint.getPosition() == Position.RIGHT){
            return upperEventPoint;
        } else {
            return lowerEventPoint;
        }
    }

    public double[] vector(){
        double[] vector = {lowerEventPoint.getXCoords() - upperEventPoint.getXCoords(), lowerEventPoint.getYCoords() - upperEventPoint.getYCoords()};
        return vector;
    }

}
