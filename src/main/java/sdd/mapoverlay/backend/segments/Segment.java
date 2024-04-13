package sdd.mapoverlay.backend.segments;

import sdd.mapoverlay.backend.Logic;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.Position;

public class Segment implements Comparable<Segment> {

    private EventPoint upperEndPoint;
    private EventPoint lowerEndPoint;
    private int id;
    private Vector vector;

    /**
     * Constructeur de la classe Segment
     *
     * @param upperEndPoint le point de l'extremité supérieure du segment
     * @param lowerEndPoint le point de l'extremité inférieure du segment
     */
    public Segment(EventPoint upperEndPoint, EventPoint lowerEndPoint) {
        this.upperEndPoint = upperEndPoint;
        this.lowerEndPoint = lowerEndPoint;
        this.vector = computeVector();
    }

    /**
     * Constructeur de la classe Segment a partir de coordonnées de points dont on
     * ne connait pas encore le point superieur et inferieur. On les détermine en
     * fonction de leur position.
     *
     * @param x1 coordonnée x du premier point
     * @param y1 coordonnée y du premier point
     * @param x2 coordonnée x du second point
     * @param y2 coordonnée y du second point
     */
    public Segment (double x1, double y1, double x2, double y2){
        try {
            if (y1 > y2){
                this.upperEndPoint = new EventPoint(x1, y1);
                this.lowerEndPoint = new EventPoint(x2, y2);
            } else if (y1 == y2){ // horizontal
                if (x1 < x2){
                    this.upperEndPoint = new EventPoint(x1, y1);
                    this.lowerEndPoint = new EventPoint(x2, y2);
                } else if (x1 > x2){
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

            if (upperEndPoint.getX() < lowerEndPoint.getX()){
                upperEndPoint.setPosition(Position.LEFT);
                lowerEndPoint.setPosition(Position.RIGHT);
            } else if (upperEndPoint.getX() > lowerEndPoint.getX()){
                upperEndPoint.setPosition(Position.RIGHT);
                lowerEndPoint.setPosition(Position.LEFT);
            } else { // x egaux
                upperEndPoint.setPosition(Position.LEFT);
                lowerEndPoint.setPosition(Position.RIGHT);
            }
        } catch( Exception e){
            System.out.println(e);
        }
    }

    /**
     * Permet de recuperer le point superieur du segment
     *
     * @return un EventPoint le point superieur du segment
     */
    public EventPoint getUpperEndPoint() {
        return upperEndPoint;
    }

    /**
     * Permet de recuperer le point inferieur du segment
     *
     * @return un EventPoint le point inferieur du segment
     */
    public EventPoint getLowerEndPoint() {
        return lowerEndPoint;
    }

    /**
     * Permet de recuperer le point gauche du segment
     *
     * @return un EventPoint le point gauche du segment
     */
    public EventPoint getLeftEndPoint() {
        if (upperEndPoint.getPosition() == Position.LEFT) {
            return upperEndPoint;
        } else {
            return lowerEndPoint;
        }
    }

    /**
     * Permet de definir l'identifiant du segment
     *
     * @param id l'identifiant du segment
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Permet de recuperer l'identifiant du segment
     *
     * @return un String l'identifiant du segment
     */
    public int getId() {
        return this.id;
    }

    /**
     * Permet de recuperer le point droit du segment
     *
     * @return un EventPoint le point droit du segment
     */
    public EventPoint getRightEndPoint() {
        if (upperEndPoint.getPosition() == Position.RIGHT) {
            return upperEndPoint;
        } else {
            return lowerEndPoint;
        }
    }

    /**
     * Permet de calculer le vecteur du segment
     *
     * @return un Vector le vecteur du segment
     */
    public Vector computeVector() {
        double u = upperEndPoint.getX() - lowerEndPoint.getX();
        double v = upperEndPoint.getY() - lowerEndPoint.getY();
        return new Vector(u, v);
    }

    /**
     * Fonction permettant de representer un segment sous forme de chaine de
     * caractères
     *
     * @return un String la chaine de caractères representant le segment
     */
    @Override
    public String toString() {
        return upperEndPoint.toString() + " " + lowerEndPoint.toString();
        // return id;
    }

    /**
     * Fonction permettant de comparer deux segments
     *
     * @param o le segment a comparer
     * @return un int 0 si les deux segments sont confondus, 1 si le segment est a
     *         droite du segment o, -1 si le segment est a gauche du segment o, 2 si
     *         les deux segments sont colineaires
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
     * Fonction permettant de verifier si un point est contenu dans le segment
     *
     * @param p le point a verifier
     * @return un boolean true si le point est contenu dans le segment, false sinon
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
//            return (double) Math.round((yp - m * xp) * 100) / 100 == (double) Math.round(b * 100) / 100;
        } else {
            if (xp == x2) {
                return y2 <= yp && yp <= y1;
            } else {
                return false;
            }
        }
    }

    /**
     * Fonction permettant de calculer l'abcisse du point d'intersection entre le
     * segment et une droite horizontale d'ordonnée yp
     *
     * @param yp l'ordonnée de la droite horizontale
     * @return un double l'abcisse du point d'intersection
     */
    public double xAtYp(double yp) {

        double x1 = upperEndPoint.getX();
        double y1 = upperEndPoint.getY();
        double x2 = lowerEndPoint.getX();
        double y2 = lowerEndPoint.getY();


        if (yp < y2)
            return x2;

        if (yp > y1)
            return x1;

        if (y1 == y2)
            return x1;

        if (x1 != x2) {
            double m = (y2 - y1) / (x2 - x1);
            double b = y1 - m * x1;
//            return (double) Math.round(((yp - b) / m) * 100) /100; // /1000
            return (yp - b) / m;
        } else {
            return getLowerEndPoint().getX();
        }
    }

    /**
     * Fonction permettant de verifier si deux segments sont le meme segment
     *
     * @param other le segment a comparer
     * @return un boolean true si les deux segments sont le meme segment, false
     *         sinon
     */
    public boolean isSameSegment(Segment other) {
        return getUpperEndPoint().getX() == other.getUpperEndPoint().getX()
                && getUpperEndPoint().getY() == other.getUpperEndPoint().getY()
                && getLowerEndPoint().getX() == other.getLowerEndPoint().getX()
                && getLowerEndPoint().getY() == other.getLowerEndPoint().getY();
    }

    /**
     * Fonction permettant de recuperer les coordonnées du segment
     *
     * @return un double[] les coordonnées du segment
     */
    public double[] getSerie() {
        double x1 = upperEndPoint.getX();
        double y1 = upperEndPoint.getY();
        double x2 = lowerEndPoint.getX();
        double y2 = lowerEndPoint.getY();
        return new double[] { x1, y1, x2, y2 };
    }

    public boolean isHorizontal(){
        return upperEndPoint.getY() == lowerEndPoint.getY();
    }

}
