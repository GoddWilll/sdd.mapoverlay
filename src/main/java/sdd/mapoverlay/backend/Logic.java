package sdd.mapoverlay.backend;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.EventQueue;
import sdd.mapoverlay.backend.trees.StatusStructure;

import java.util.ArrayList;

public class Logic {

    public ArrayList<EventPoint> findIntersection(ArrayList<Segment> segments){
        Map map = new Map("fichier1.txt");
        EventQueue eventQueue = new EventQueue();
        eventQueue.initialize(map);
        StatusStructure<EventPoint> statusStructure = new StatusStructure<>();
        while (!eventQueue.isEmpty()){
            EventPoint p = determineNextEvent();
            handleEventPoint(p);
        }
        // ToDo completer la fonction
        return null;
    }

    public EventPoint determineNextEvent(){
        // ToDo
        return null;
    }

    public void handleEventPoint(EventPoint p){
        // ToDo
    }
}
