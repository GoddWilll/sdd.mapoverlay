package sdd.mapoverlay;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.EventQueue;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Map map = new Map("fichier2.txt");
        ArrayList<Segment> segments = map.getVerticallySortedSegments();

        EventQueue eventQueue = new EventQueue();
        eventQueue.initialize(map);

        eventQueue.print();

    }


}
