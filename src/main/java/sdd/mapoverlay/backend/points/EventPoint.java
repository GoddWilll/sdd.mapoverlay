package sdd.mapoverlay.backend.points;

import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.points.types.Position;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

/**
 * Classe representant un point d'événement
 */
public class EventPoint implements Comparable<EventPoint>{
    private EventType eventType;
    private Position position;
    private ArrayList<Segment> intercepts;
    private ArrayList<Segment> segments;
    private double x;
    private double y;

    /**
     * Constructeur d'un point d'événement
     * @param x coordonnée x
     * @param y coordonnée y
     * @param eventType type d'événement
     * @param position position du point
     */
    public EventPoint(double x, double y, EventType eventType, Position position) {
        this.x = x;
        this.y = y;
        this.eventType = eventType;
        this.position = position;
        this.segments = new ArrayList<>();
    }

    /**
     * Constructeur d'un point d'événement, a utiliser lors de creation de point dont on ne connait pas encore le type ni la position
     * @param x coordonnée x
     * @param y coordonnée y
     */
    public EventPoint(double x, double y){
        this.x = x;
        this.y = y;
        this.segments = new ArrayList<>();
    }


    /**
     * Constructeur d'un point d'événement (pour intersection)
     * @param x coordonnée x
     * @param y coordonnée y
     * @param eventType type d'événement
     * @param intercepts segments interceptés
     */
    public EventPoint(double x, double y, EventType eventType, ArrayList<Segment> intercepts){
        this.x = x;
        this.y = y;
        this.eventType = eventType;
        this.segments = new ArrayList<>();
        this.intercepts = intercepts;
    }

    /**
     * Permet de recuperer les segments de l'intersection
     * @return un ArrauList de segments
     */
    public ArrayList<Segment> getIntercepts(){
        return this.intercepts;
    }

    /**
     * Permet de recuperer le type d'événement
     * @return un objet EventType
     */
    public EventType getEventType(){
        return eventType;
    }

    /**
     * Permet de recuperer la position du point
     * @return un objet Position
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Permet de definir la position du point
     * @param position un objet Position
     */
    public void setPosition(Position position){
        this.position = position;
    }

    /**
     * Permet d'ajouter des segments dans la liste des segments dont le point est l'extremite superieure
     * @param segment un objet Segment
     */
    public void addSegment(Segment segment){
        this.segments.add(segment);
    }

    /**
     * Permet de recuperer la liste des segments dont le point est l'extremite superieure
     * @return un ArrayList de segments
     */
    public ArrayList<Segment> getSegments(){
        return this.segments;
    }

    /**
     * Permet de comparer deux points
     * @param o le point a comparer
     * @return un entier selon le resultat de la comparaison
     */
    @Override
    public int compareTo(EventPoint o) {
        if (getY() > o.getY()){
            return 1;
        } else if (getY() == o.getY()){
            if (getX() < o.getX()){
                return 1;
            } else if (getX() == o.getX()){
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * Permet la representation textuelle d'un point
     * @return une chaine de caractere representant le point
     */
    public String toString(){
        return getX() + " " + getY();
    }

    /**
     * Permet de recuperer la coordonnée x du point
     * @return un double
     */
    public double getX(){
        return x;
    }

    /**
     * Permet de recuperer la coordonnée y du point
     * @return
     */
    public double getY() {
        return y;
    }
}
