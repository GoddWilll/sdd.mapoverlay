package sdd.mapoverlay.backend.segments;

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.Position;


public class Segment implements Comparable<Segment> {
    private EventPoint upperEventPoint;
    private EventPoint lowerEventPoint;

    private Vector vector;

    public Segment(EventPoint upperEventPoint, EventPoint lowerEventPoint){
        this.upperEventPoint = upperEventPoint;
        this.lowerEventPoint = lowerEventPoint;
        this.vector = computeVector();
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

    public Vector getVector(){
        return vector;
    }

    public Vector computeVector(){
        double u = lowerEventPoint.getXCoords() - upperEventPoint.getXCoords();
        double v = lowerEventPoint.getYCoords() - upperEventPoint.getYCoords();
        return new Vector(u, v);
    }

    public Position determinePosition(EventPoint point){
        double vectorProduct = point.getXCoords() * vector.getY() - point.getYCoords() * vector.getX();
        if (vectorProduct < 0){
            return Position.LEFT;
        } else if (vectorProduct > 0){
            return Position.RIGHT;
        } else {
            return null; // sur le segment
        }
    }

    public Position determinePosition(Segment segment){
        double vectorProduct = segment.getVector().getX() * vector.getY() - segment.getVector().getY() * vector.getX();
        if (vectorProduct < 0){
            return Position.LEFT;
        } else if (vectorProduct > 0){
            return Position.RIGHT;
        } else {
            return Position.COLLINEAR;
        }
    }

    @Override
    public String toString(){
        return upperEventPoint.toString() + " " + lowerEventPoint.toString();
    }

    @Override
    public int compareTo(Segment o) {
        double vectorProduct = vector.getX() * o.vector.getY() - vector.getY() * o.vector.getX();
        if (vectorProduct < 0){
            return -1;
        } else if (vectorProduct > 0){
            return 1;
        } else {
            if (o.getUpperEndPoint().getXCoords() == upperEventPoint.getXCoords()){
                return 0; // confondus
            } else {
                return 2; // collineaires
            }
        }
    }

    public boolean containsPoint(EventPoint p){
        double x1 = upperEventPoint.getXCoords();
        double y1 = upperEventPoint.getYCoords();
        double x2 = lowerEventPoint.getXCoords();
        double y2 = lowerEventPoint.getYCoords();
        double xp = p.getXCoords();
        double yp = p.getYCoords();
        if (x1 != x2){
            double m = (y2 - y1)/(x2 - x1);
            double b = y1 - m * x1;
            return yp - m * xp == b;
        } else {
            if (xp == x2){
                return y2 <= yp && yp <= y1;
            } else {
                return false;
            }
        }
    }
}
