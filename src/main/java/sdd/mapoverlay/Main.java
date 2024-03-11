package sdd.mapoverlay;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.EventQueue;
import sdd.mapoverlay.backend.trees.StatusStructure;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Map map = new Map("fichier2.txt");
        DecimalFormat df = new DecimalFormat("#.##");

        EventQueue eventQueue = new EventQueue();
        eventQueue.initialize(map);
        StatusStructure<EventPoint> status = new StatusStructure<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++){
            EventPoint ep = new EventPoint(Double.parseDouble(df.format(random.nextDouble()).replace(',', '.')), Double.parseDouble(df.format(random.nextDouble()).replace(',', '.')));
            status.insert(ep);
        }
        status.print("", true);



    }


}
