package sdd.mapoverlay.backend.points;

import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.points.types.Position;
import sdd.mapoverlay.backend.segments.Segment;
public class EventPoint extends Point implements Comparable<Point>{
    private EventType eventType;
    private Position position;
    private Segment segment;
    public EventPoint(double xCoord, double yCoord, EventType eventType, Position position) {
        super(xCoord, yCoord);
        this.eventType = eventType;
        this.position = position;
    }

    public EventPoint(double xCoord, double yCoord){
        super(xCoord, yCoord);
    }

    public EventType getEventType(){
        return eventType;
    }

    public Position getPosition(){
        return position;
    }

    public void setSegment(Segment segment){
        this.segment = segment;
    }

    public Segment getSegment(){
        return segment;
    }
    @Override
    public int compareTo(Point o) {
        if (getYCoords() > o.getYCoords()){
            return 1;
        } else if (getYCoords() == o.getYCoords()){
            if (getXCoords() < o.getXCoords()){
                return 1;
            } else if (getXCoords() == o.getXCoords()){
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public String toString(){
        return getXCoords() + " " + getYCoords();
    }
}
