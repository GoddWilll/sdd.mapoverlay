package sdd.mapoverlay.backend.segments;

import javafx.geometry.Pos;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.Position;


public class Segment implements Comparable<Segment> {
    private EventPoint upperEventPoint;
    private EventPoint lowerEventPoint;

    private String id;

    private Vector vector;

    public Segment(EventPoint upperEventPoint, EventPoint lowerEventPoint){
        this.upperEventPoint = upperEventPoint;
        this.lowerEventPoint = lowerEventPoint;
        this.vector = computeVector();
    }

    public Segment (double x1, double y1, double x2, double y2) throws  Exception{
        try {
            if (y1 > y2){
                this.upperEventPoint = new EventPoint(x1, y1);
                this.lowerEventPoint = new EventPoint(x2, y2);
            } else if (y1 == y2){
                if (x1 > x2){
                    this.upperEventPoint = new EventPoint(x1, y1);
                    this.lowerEventPoint = new EventPoint(x2, y2);
                } else if (x1 < x2){
                    this.upperEventPoint = new EventPoint(x2, y2);
                    this.lowerEventPoint = new EventPoint(x1, y1);
                } else {
                    throw new Exception("Ce segment est un point.");
                }
            } else {
                this.upperEventPoint = new EventPoint(x2, y2);
                this.lowerEventPoint = new EventPoint(x1, y1);
            }
            this.vector = computeVector();

            if (upperEventPoint.getXCoords() < lowerEventPoint.getXCoords()){
                upperEventPoint.setPosition(Position.LEFT);
                lowerEventPoint.setPosition(Position.RIGHT);
            } else if (upperEventPoint.getXCoords() > lowerEventPoint.getXCoords()){
                upperEventPoint.setPosition(Position.RIGHT);
                lowerEventPoint.setPosition(Position.LEFT);
            } else { // x egaux
                upperEventPoint.setPosition(Position.LEFT);
                lowerEventPoint.setPosition(Position.RIGHT);
            }
        } catch( Exception e){
            System.out.println(e);
        }
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

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
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
        double u = upperEventPoint.getXCoords() - lowerEventPoint.getXCoords();
        double v = upperEventPoint.getYCoords() - lowerEventPoint.getYCoords();
        return new Vector(u, v);
    }

    public Position determinePosition(EventPoint point){
        double vectorProduct = point.getXCoords() * vector.getY() - point.getYCoords() * vector.getX();
        if (vectorProduct < 0){
            return Position.LEFT;
        } else if (vectorProduct > 0){
            return Position.RIGHT;
        } else {
            System.out.println(point);
            return Position.COLLINEAR; // sur le segment
        }
    }


//    public double angleBetweenVectors(Vector v1, Vector v2){
//        double product = v1.getX() * v2.getX() + v1.getY() * v2.getY();
//        double mag1 = Math.sqrt(Math.pow(v1.getX(), 2) + Math.pow(v1.getY(), 2));
//        double mag2 = Math.sqrt(Math.pow(v2.getX(), 2) + Math.pow(v2.getY(), 2));
//        double cosTheta = product / (mag1 * mag2);
//        return Math.acos(cosTheta);
//    }

//    public Position pointRelativeToSegment(EventPoint p){
//        Vector v = getVector();
//        Vector v1 = new Vector(p.getXCoords() - lowerEventPoint.getXCoords(), p.getYCoords() - lowerEventPoint.getYCoords());
//        Vector v2 = new Vector(p.getXCoords() - upperEventPoint.getXCoords(), p.getYCoords() - upperEventPoint.getYCoords());
//
//        double angle1 = angleBetweenVectors(v, v1);
//        double angle2 = angleBetweenVectors(v, v2);
//
//        if (angle1 < Math.PI / 2 && angle2 < Math.PI / 2){
//            return Position.LEFT;
//        } else if (angle1 > Math.PI / 2 && angle2 > Math.PI / 2){
//            return Position.RIGHT;
//        } else {
//            System.out.println(angle1);
//            System.out.println(angle2);
//            return Position.COLLINEAR;
//        }
//    }


    public Position pos (EventPoint p){
        EventPoint a = lowerEventPoint;
        EventPoint b = upperEventPoint;
        double position = (b.getXCoords() - a.getXCoords()) * (p.getYCoords() - a.getYCoords()) - (b.getYCoords() - a.getYCoords()) * (p.getXCoords() - a.getXCoords());

        if (position > 0){
            return Position.LEFT; // point a gauche du segment
        } else if (position < 0){
            return Position.RIGHT; // point a droite du segment
        } else {
            return Position.COLLINEAR;
        }
    }

//    public Position determinePosition(Segment segment){
//        Vector v1 = getVector();
//        Vector v2 = segment.getVector();
//        Vector link  = new Vector(v2.getX() - v1.getX(), v2.getY() - v1.getY());
//        double product = v1.getX() * link.getY() - v1.getY() * link.getX();
//        if (product > 0) {
//            return Position.LEFT;
//        } else if (product < 0){
//            return Position.RIGHT;
//        } else {
//            return Position.COLLINEAR;
//        }
//    }

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
//        return id;
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

    public double xAtYp(double yp) {
        double x1 = upperEventPoint.getXCoords();
        double y1 = upperEventPoint.getYCoords();
        double x2 = lowerEventPoint.getXCoords();
        double y2 = lowerEventPoint.getYCoords();
        if (x1 != x2) {
            double m = (y2 - y1) / (x2 - x1);
            double b = y1 - m * x1;
            return Math.round(((yp - b) / m) * 1000)/1000;
        } else {
            return getLeftEndPoint().getXCoords();
        }
    }
}
