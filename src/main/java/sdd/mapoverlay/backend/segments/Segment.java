package sdd.mapoverlay.backend.segments;

import sdd.mapoverlay.backend.Logic;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.Position;

/**
 * Represents a segment in a map overlay.
 */
public class Segment implements Comparable<Segment> {

    private EventPoint upperEndPoint;
    private EventPoint lowerEndPoint;
    private int id;
    private Vector vector;

    /**
     * Constructs a segment with the given upper and lower end points.
     *
     * @param upperEndPoint The upper end point of the segment.
     * @param lowerEndPoint The lower end point of the segment.
     */
    public Segment(EventPoint upperEndPoint, EventPoint lowerEndPoint) {
        this.upperEndPoint = upperEndPoint;
        this.lowerEndPoint = lowerEndPoint;
        this.vector = computeVector();
    }

    /**
     * Constructs a segment with the given coordinates of the upper and lower end
     * points.
     *
     * @param x1 The x-coordinate of the upper end point.
     * @param y1 The y-coordinate of the upper end point.
     * @param x2 The x-coordinate of the lower end point.
     * @param y2 The y-coordinate of the lower end point.
     */
    public Segment(double x1, double y1, double x2, double y2) {
        try {
            if (y1 > y2) {
                this.upperEndPoint = new EventPoint(x1, y1);
                this.lowerEndPoint = new EventPoint(x2, y2);
            } else if (y1 == y2) { // horizontal
                if (x1 < x2) {
                    this.upperEndPoint = new EventPoint(x1, y1);
                    this.lowerEndPoint = new EventPoint(x2, y2);
                } else if (x1 > x2) {
                    this.upperEndPoint = new EventPoint(x2, y2);
                    this.lowerEndPoint = new EventPoint(x1, y1);
                } else {
                    throw new Exception("Ce segment est un point.");
                }
            } else {
                this.upperEndPoint = new EventPoint(x2, y2);
                this.lowerEndPoint = new EventPoint(x1, y1);
            }
            this.vector = computeVector();

            if (upperEndPoint.getX() < lowerEndPoint.getX()) {
                upperEndPoint.setPosition(Position.LEFT);
                lowerEndPoint.setPosition(Position.RIGHT);
            } else if (upperEndPoint.getX() > lowerEndPoint.getX()) {
                upperEndPoint.setPosition(Position.RIGHT);
                lowerEndPoint.setPosition(Position.LEFT);
            } else { // x egaux
                upperEndPoint.setPosition(Position.LEFT);
                lowerEndPoint.setPosition(Position.RIGHT);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Returns the upper end point of the segment.
     *
     * @return The upper end point.
     */
    public EventPoint getUpperEndPoint() {
        return upperEndPoint;
    }

    /**
     * Returns the lower end point of the segment.
     *
     * @return The lower end point.
     */
    public EventPoint getLowerEndPoint() {
        return lowerEndPoint;
    }

    /**
     * Returns the left end point of the segment.
     *
     * @return The left end point.
     */
    public EventPoint getLeftEndPoint() {
        if (upperEndPoint.getPosition() == Position.LEFT) {
            return upperEndPoint;
        } else {
            return lowerEndPoint;
        }
    }

    /**
     * Sets the ID of the segment.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the segment.
     *
     * @return The ID of the segment.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the right end point of the segment.
     *
     * @return The right end point.
     */
    public EventPoint getRightEndPoint() {
        if (upperEndPoint.getPosition() == Position.RIGHT) {
            return upperEndPoint;
        } else {
            return lowerEndPoint;
        }
    }

    /**
     * Computes and returns the vector representation of the segment.
     *
     * @return The vector representation of the segment.
     */
    public Vector computeVector() {
        double u = upperEndPoint.getX() - lowerEndPoint.getX();
        double v = upperEndPoint.getY() - lowerEndPoint.getY();
        return new Vector(u, v);
    }

    /**
     * Returns a string representation of the segment.
     *
     * @return A string representation of the segment.
     */
    @Override
    public String toString() {
        return upperEndPoint.toString() + " " + lowerEndPoint.toString();
        // return id;
    }

    /**
     * Compares this segment with the specified segment for order.
     *
     * @param o The segment to be compared.
     * @return A negative integer, zero, or a positive integer as this segment is
     *         less than, equal to, or greater than the specified segment.
     */
    @Override
    public int compareTo(Segment o) {
        double vectorProduct = vector.getX() * o.vector.getY() - vector.getY() * o.vector.getX();
        if (vectorProduct < 0) {
            return -1;
        } else if (vectorProduct > 0) {
            return 1;
        } else {

            if (o.getUpperEndPoint().getX() == upperEndPoint.getX()) {
                return 0;
            } else {
                return 2;
            }
        }
    }

    /**
     * Checks if the segment contains the specified point.
     *
     * @param p The point to check.
     * @return true if the segment contains the point, false otherwise.
     */
    public boolean containsPoint(EventPoint p) {
        double x1 = upperEndPoint.getX();
        double y1 = upperEndPoint.getY();
        double x2 = lowerEndPoint.getX();
        double y2 = lowerEndPoint.getY();
        double xp = p.getX();
        double yp = p.getY();
        if (x1 != x2) {
            double m = (y2 - y1) / (x2 - x1);
            double b = (y1 - m * x1);
            return Math.abs(yp - m * xp - b) < Logic.EPSILON;
        } else {
            if (xp == x2) {
                return y2 <= yp && yp <= y1;
            } else {
                return false;
            }
        }
    }

    /**
     * Returns the x-coordinate of the segment at the specified y-coordinate of the
     * point.
     *
     * @param p    The point.
     * @param diff The difference in y-coordinate.
     * @return The x-coordinate of the segment at the specified y-coordinate of the
     *         point.
     */
    public double xAtYp(EventPoint p, double diff) {
        double yp = p.getY() - diff;
        double x1 = upperEndPoint.getX();
        double y1 = upperEndPoint.getY();
        double x2 = lowerEndPoint.getX();
        double y2 = lowerEndPoint.getY();

        if (yp < y2)
            return x2;

        if (yp > y1)
            return x1;

        if (y1 == y2)
            return p.getX() + 0.0001;

        if (x1 != x2) {
            double m = (y2 - y1) / (x2 - x1);
            double b = y1 - m * x1;
            return (yp - b) / m;
        } else {
            return getLowerEndPoint().getX();
        }
    }

    /**
     * Returns the x-coordinate of the segment at the specified y-coordinate of the
     * point.
     *
     * @param p The point.
     * @return The x-coordinate of the segment at the specified y-coordinate of the
     *         point.
     */
    public double xAtYp(EventPoint p) {
        double yp = p.getY();

        double x1 = upperEndPoint.getX();
        double y1 = upperEndPoint.getY();
        double x2 = lowerEndPoint.getX();
        double y2 = lowerEndPoint.getY();

        if (yp < y2)
            return x2;

        if (yp > y1)
            return x1;

        if (y1 == y2)
            return p.getX();

        if (x1 != x2) {
            double m = (y2 - y1) / (x2 - x1);
            double b = y1 - m * x1;
            return (yp - b) / m;
        } else {
            return getLowerEndPoint().getX();
        }
    }

    /**
     * Checks if this segment is the same as the specified segment.
     *
     * @param other The segment to compare.
     * @return true if the segments are the same, false otherwise.
     */
    public boolean isSameSegment(Segment other) {
        return getUpperEndPoint().getX() == other.getUpperEndPoint().getX()
                && getUpperEndPoint().getY() == other.getUpperEndPoint().getY()
                && getLowerEndPoint().getX() == other.getLowerEndPoint().getX()
                && getLowerEndPoint().getY() == other.getLowerEndPoint().getY();
    }

    /**
     * Returns the coordinates of the segment as an array.
     *
     * @return The coordinates of the segment as an array.
     */
    public double[] getSerie() {
        double x1 = upperEndPoint.getX();
        double y1 = upperEndPoint.getY();
        double x2 = lowerEndPoint.getX();
        double y2 = lowerEndPoint.getY();
        return new double[] { x1, y1, x2, y2 };
    }

    /**
     * Checks if the segment is horizontal.
     *
     * @return true if the segment is horizontal, false otherwise.
     */
    public boolean isHorizontal() {
        return upperEndPoint.getY() == lowerEndPoint.getY();
    }

}
