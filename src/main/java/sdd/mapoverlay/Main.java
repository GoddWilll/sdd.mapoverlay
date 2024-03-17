package sdd.mapoverlay;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.trees.EventQueue;
import sdd.mapoverlay.backend.trees.StatusStructure;


import java.text.DecimalFormat;


public class Main {
    public static void main(String[] args){
        Map map = new Map("fichier1.txt");
        DecimalFormat df = new DecimalFormat("#.##");

        EventQueue eventQueue = new EventQueue();
        eventQueue.initialize(map);
        StatusStructure<EventPoint> status = new StatusStructure<>();

        for (double i = 0; i < 0.6; i+= 0.1){
            EventPoint ep = new EventPoint(Double.parseDouble(df.format(i * 2).replace(',', '.')), Double.parseDouble(df.format(i).replace(',', '.')));
            status.insert(ep);
        }
        EventPoint tester = new EventPoint(0.9, 0.9);

        status.insert(tester);

        status.print("", true);

        System.out.println("---------------------------------------------------------");

        status.suppress(tester);

        status.print("", true);

        EventPoint ep = new EventPoint(0.2, 0.2);

        status.suppressStatusStructure(ep);

        System.out.println("---------------------------------------------------------");

        status.print("", true);

    }




}
