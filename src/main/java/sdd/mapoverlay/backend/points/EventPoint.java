package sdd.mapoverlay.backend.points;

import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.points.types.Position;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

public class EventPoint extends Point implements Comparable<Point>{
    private EventType eventType;
    private Position position;
    private ArrayList<Segment> segments;
    public EventPoint(double xCoord, double yCoord, EventType eventType, Position position) {
        super(xCoord, yCoord);
        this.eventType = eventType;
        this.position = position;
        this.segments = new ArrayList<>();
    }

    public EventPoint(double xCoord, double yCoord){
        super(xCoord, yCoord);
        this.segments = new ArrayList<>();
    }

    public EventType getEventType(){
        return eventType;
    }

    public Position getPosition(){
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void addSegment(Segment segment){
        this.segments.add(segment);
    }

    public ArrayList<Segment> getSegments(){
        return this.segments;
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
