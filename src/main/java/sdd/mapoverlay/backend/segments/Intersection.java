package sdd.mapoverlay.backend.segments;

import sdd.mapoverlay.backend.points.EventPoint;

import java.util.ArrayList;

public class Intersection {

    private EventPoint p;
    private ArrayList<Segment> U;
    private ArrayList<Segment> C;
    private ArrayList<Segment> L;

    public Intersection(EventPoint p, ArrayList<Segment> U, ArrayList<Segment> C, ArrayList<Segment> L){
        this.p = p;
        this.U = U;
        this.C = C;
        this.L = L;
    }


    public EventPoint getP(){
        return this.p;
    }

    public ArrayList<Segment> getU(){
        return this.U;
    }

    public ArrayList<Segment> getC(){
        return this.C;
    }

    public ArrayList<Segment> getL(){
        return this.L;
    }

    @Override
    public String toString(){
        return p.toString();
    }

}
