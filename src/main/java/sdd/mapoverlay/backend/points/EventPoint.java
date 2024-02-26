package sdd.mapoverlay.backend.points;

import sdd.mapoverlay.backend.points.types.EventType;

public class EventPoint extends Point{
    private EventType eventType;
    public EventPoint(double x_coord, double y_coord, EventType eventType) {
        super(x_coord, y_coord);
        this.eventType = eventType;
    }

    public EventType getEventType(){
        return eventType;
    }
}
