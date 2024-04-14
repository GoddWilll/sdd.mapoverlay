package sdd.mapoverlay.backend.segments;

import sdd.mapoverlay.backend.points.EventPoint;

import java.util.ArrayList;

/**
 * Represents an intersection between multiple segments.
 */
public class Intersection {

    private EventPoint p;
    private ArrayList<Segment> U;
    private ArrayList<Segment> C;
    private ArrayList<Segment> L;

    /**
     * Constructs a new Intersection object.
     *
     * @param p The event point representing the intersection.
     * @param U The list of segments that are above the intersection.
     * @param C The list of segments that cross the intersection.
     * @param L The list of segments that are below the intersection.
     */
    public Intersection(EventPoint p, ArrayList<Segment> U, ArrayList<Segment> C, ArrayList<Segment> L) {
        this.p = p;
        this.U = U;
        this.C = C;
        this.L = L;
    }

    /**
     * Returns the event point representing the intersection.
     *
     * @return The event point.
     */
    public EventPoint getP() {
        return this.p;
    }

    /**
     * Returns the list of segments that are above the intersection.
     *
     * @return The list of segments.
     */
    public ArrayList<Segment> getU() {
        return this.U;
    }

    /**
     * Returns the list of segments that cross the intersection.
     *
     * @return The list of segments.
     */
    public ArrayList<Segment> getC() {
        return this.C;
    }

    /**
     * Returns the list of segments that are below the intersection.
     *
     * @return The list of segments.
     */
    public ArrayList<Segment> getL() {
        return this.L;
    }

    /**
     * Returns a string representation of the Intersection object.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return p.toString();
    }

}
