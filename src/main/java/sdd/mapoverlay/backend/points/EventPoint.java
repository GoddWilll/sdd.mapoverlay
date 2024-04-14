package sdd.mapoverlay.backend.points;

import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.points.types.Position;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

/**
 * Represents an event point on a map.
 */
public class EventPoint implements Comparable<EventPoint> {
    private EventType eventType;
    private Position position;
    private ArrayList<Segment> intercepts;
    private ArrayList<Segment> segments;
    private double x;
    private double y;

    /**
     * Constructs an EventPoint object with the specified coordinates, event type,
     * and position.
     *
     * @param x         the x-coordinate of the event point
     * @param y         the y-coordinate of the event point
     * @param eventType the type of the event
     * @param position  the position of the event
     */
    public EventPoint(double x, double y, EventType eventType, Position position) {
        this.x = x;
        this.y = y;
        this.eventType = eventType;
        this.position = position;
        this.segments = new ArrayList<>();
    }

    /**
     * Constructs an EventPoint object with the specified coordinates.
     *
     * @param x the x-coordinate of the event point
     * @param y the y-coordinate of the event point
     */
    public EventPoint(double x, double y) {
        this.x = x;
        this.y = y;
        this.segments = new ArrayList<>();
    }

    /**
     * Constructs an EventPoint object with the specified coordinates, event type,
     * and intercepts.
     *
     * @param x          the x-coordinate of the event point
     * @param y          the y-coordinate of the event point
     * @param eventType  the type of the event
     * @param intercepts the intercepts of the event
     */
    public EventPoint(double x, double y, EventType eventType, ArrayList<Segment> intercepts) {
        this.x = x;
        this.y = y;
        this.eventType = eventType;
        this.segments = new ArrayList<>();
        this.intercepts = intercepts;
    }

    /**
     * Returns the intercepts of the event point.
     *
     * @return the intercepts of the event point
     */
    public ArrayList<Segment> getIntercepts() {
        return this.intercepts;
    }

    /**
     * Returns the event type of the event point.
     *
     * @return the event type of the event point
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns the position of the event point.
     *
     * @return the position of the event point
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the event point.
     *
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Adds a segment to the event point.
     *
     * @param segment the segment to add
     */
    public void addSegment(Segment segment) {
        this.segments.add(segment);
    }

    /**
     * Returns the segments of the event point.
     *
     * @return the segments of the event point
     */
    public ArrayList<Segment> getSegments() {
        return this.segments;
    }

    @Override
    public int compareTo(EventPoint o) {
        if (getY() > o.getY()) {
            return 1;
        } else if (getY() == o.getY()) {
            if (getX() < o.getX()) {
                return 1;
            } else if (getX() == o.getX()) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * Returns a string representation of the event point.
     *
     * @return a string representation of the event point
     */
    public String toString() {
        return getX() + " " + getY();
    }

    /**
     * Returns the x-coordinate of the event point.
     *
     * @return the x-coordinate of the event point
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the event point.
     *
     * @return the y-coordinate of the event point
     */
    public double getY() {
        return y;
    }
}
