package sdd.mapoverlay.backend;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.EventQueue;
import sdd.mapoverlay.backend.trees.StatusStructure;

import java.util.ArrayList;

public class Logic {

    private static StatusStructure statusStructure = new StatusStructure();
    private static EventQueue eventQueue = new EventQueue();

    public static ArrayList<EventPoint> findIntersection(ArrayList<Segment> segments){
        Map map = new Map("fichier1.txt");
        eventQueue.initialize(map);
        while (!eventQueue.isEmpty()){
            EventPoint p = determineNextEvent(eventQueue);
            handleEventPoint(p);
        }
        // ToDo completer la fonction
        return null;
    }

    public static EventPoint determineNextEvent(EventQueue eventQueue){
        return  eventQueue.suppressMin();
    }

    public static void handleEventPoint(EventPoint p){
        ArrayList<Segment> U = p.getSegments();
        // trouver tous les segments dans T qui contiennent p (?????)


    }

    public static void findNewEvent(){
        // Todo
    }
}
