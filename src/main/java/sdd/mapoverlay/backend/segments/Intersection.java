package sdd.mapoverlay.backend.segments;

import sdd.mapoverlay.backend.points.EventPoint;

import java.util.ArrayList;

public class Intersection {

    private EventPoint p;
    private ArrayList<Segment> U;
    private ArrayList<Segment> C;
    private ArrayList<Segment> L;

    /**
     * Constructeur d'une intersection
     * @param p point d'intersection
     * @param U segments dont p est l'extremite superieure
     * @param C segments contenant p
     * @param L segments dont p est l'extremite inferieure
     */
    public Intersection(EventPoint p, ArrayList<Segment> U, ArrayList<Segment> C, ArrayList<Segment> L){
        this.p = p;
        this.U = U;
        this.C = C;
        this.L = L;
    }


    /**
     * Permet de recuperer le point d'intersection
     * @return EventPoint le point d'intersection
     */
    public EventPoint getP(){
        return this.p;
    }

    /**
     * Permet de recuprer la liste des segments dont p est l'extremite superieure
     * @return ArrayList<Segment> les segments dont p est l'extremite superieure
     */
    public ArrayList<Segment> getU(){
        return this.U;
    }

    /**
     * Permet de recuprer la liste des segments contenant p
     * @return ArrayList<Segment> les segments contenant p
     */
    public ArrayList<Segment> getC(){
        return this.C;
    }

    /**
     * Permet de recuprer la liste des segments dont p est l'extremite inferieure
     * @return ArrayList<Segment> les segments dont p est l'extremite inferieure
     */
    public ArrayList<Segment> getL(){
        return this.L;
    }

    /**
     * Permet de recuperer le point d'intersection sous forme de chaine de caracteres
     * @return String une chaine de caracteres representant le point d'intersection
     */
    @Override
    public String toString(){
        return p.toString();
    }

}
