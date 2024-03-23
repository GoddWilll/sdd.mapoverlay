package sdd.mapoverlay;

import org.w3c.dom.events.Event;
import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.EventQueue;
import sdd.mapoverlay.backend.trees.StatusStructure;
import sdd.mapoverlay.backend.Logic;

import java.text.DecimalFormat;


public class Main {
    public static void main(String[] args) {
        Map map = new Map("test.txt");
        DecimalFormat df = new DecimalFormat("#.##");

        EventQueue eventQueue = new EventQueue();
        eventQueue.initialize(map);
        StatusStructure status = new StatusStructure();
        eventQueue.print("", true);
        System.out.println("------------------");
        while(!eventQueue.isEmpty()){
            EventPoint nextEvent = eventQueue.suppressMin();
            if (nextEvent.getEventType() == EventType.UPPERENDPOINT){
                if (nextEvent.getSegments().size() > 1){
                    for (Segment segment : nextEvent.getSegments()){
                        status.insertStatusStructureVariant(segment);
                    }
                } else {
                    status.insertStatusStructureVariant(nextEvent.getSegments().getFirst());
                }
            }
        }
        Segment seg = new Segment(new EventPoint(0.15, 0.15), new EventPoint(0.34, 0.56));
        status.insertStatusStructureVariant(seg);

        status.print("", true);
        System.out.println("------------------");
        status.suppressStatusStructure(new Segment(new EventPoint(0.15, 0.15), new EventPoint(0.34, 0.56)));
        status.print("", true);


    }




}
