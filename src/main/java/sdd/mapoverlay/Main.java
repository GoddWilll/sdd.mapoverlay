package sdd.mapoverlay;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.trees.EventQueue;
import sdd.mapoverlay.backend.trees.StatusStructure;


import java.text.DecimalFormat;


public class Main {
    public static void main(String[] args){
        Map map = new Map("fichier2.txt");
        DecimalFormat df = new DecimalFormat("#.##");

        EventQueue eventQueue = new EventQueue();
        eventQueue.initialize(map);
        StatusStructure<EventPoint> status = new StatusStructure<>();

        for (double i = 0; i < 0.6; i+= 0.1){
            EventPoint ep = new EventPoint(0, Double.parseDouble(df.format(i).replace(',', '.')));
            status.insert(ep);
        }
        EventPoint tester = new EventPoint(0.22, 0.22);

        status.insert(tester);

        status.print("", true);

        System.out.println("---------------------------------------------------------");

        status.suppress(tester);

        status.print("", true);
    }


}
