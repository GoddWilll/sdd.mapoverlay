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
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws Exception {
//        Map map = new Map("test.txt");
//        EventQueue eventQueue = new EventQueue();
//        eventQueue.initialize(map);
//        StatusStructure status = new StatusStructure();
////        eventQueue.print("", true);
////        System.out.println("------------------");
//        while(!eventQueue.isEmpty()){
//            EventPoint nextEvent = eventQueue.suppressMin();
//            if (nextEvent.getEventType() == EventType.UPPERENDPOINT){
//                if (nextEvent.getSegments().size() > 1){
//                    for (Segment segment : nextEvent.getSegments()){
//                        status.insertStatusStructureVariant(segment);
//                    }
//                } else {
//                    status.insertStatusStructureVariant(nextEvent.getSegments().getFirst());
//                }
//            }
//        }
//        Segment seg = new Segment(new EventPoint(0.15, 0.15), new EventPoint(0.34, 0.56));
//        status.insertStatusStructureVariant(seg);
//
////        status.print("", true);
////        System.out.println("------------------");
////        status.suppressStatusStructure(new Segment(new EventPoint(0.15, 0.15), new EventPoint(0.34, 0.56)));
////        status.print("", true);
////        status.suppressStatusStructure(new Segment(new EventPoint(75.91, 199.52), new EventPoint( 122.97, 95.32)));
////        status.print("", true);
//
//        System.out.println(status.getLeft().getFather());

        StatusStructure status = new StatusStructure();

//        Segment seg1 = new Segment(1, 1,  5, 5);
//        Segment seg2 = new Segment(1, 5, 4, 2);
//        Segment seg3 = new Segment(3, 5, 6, 2);
//        Segment seg4 = new Segment(5, 5, 1, 2);
//        Segment seg5 = new Segment(2, 4, 3, 7);
//        Segment seg6 = new Segment(1, 2, 1, 4);
//        Segment seg7 = new Segment(7, 3, 2, 3);
//        Segment seg8 = new Segment(4, 5, 8, 0);
        Segment seg1 = new Segment(100, 100,  0, 0);
        Segment seg2 = new Segment(100, 200, 400, 100);
        Segment seg3 = new Segment(300, 300, 600, 200);
        Segment seg4 = new Segment(500, 400, 100, 300);
        Segment seg5 = new Segment(200, 500, 300, 400);
        Segment seg6 = new Segment(100, 600, 100, 500);
        Segment seg7 = new Segment(700, 700, 200, 600);
        Segment seg8 = new Segment(400, 800, 800, 700);

        Segment[] segments = {seg8, seg7, seg6, seg5, seg4, seg3, seg2, seg1};
        int incr = 1;
        for (Segment segment : segments){
            segment.setId("seg" + incr);
            incr++;
            status.insertStatusStructureVariant(segment);
            status.print("", true);
            System.out.println("-------------------------------------");
        }

        EventPoint p = new EventPoint(300, 300);
        System.out.println(status.segmentsContainingP(p));
        System.out.println(seg2.pos(p));


    }




}
