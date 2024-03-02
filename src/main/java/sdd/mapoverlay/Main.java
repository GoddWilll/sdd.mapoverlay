package sdd.mapoverlay;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Map map = new Map("fichier1.txt");
        ArrayList<Segment> segments = map.getVerticallySortedSegments();
        double minY = segments.getLast().getLowerEndPoint().getYCoords();
        double maxY = segments.getFirst().getUpperEndPoint().getYCoords();
        int segmentIncrement = 0;
        for (double i = maxY; i > minY; i-= 0.01){
            i = (double) Math.round(i * 100) / 100;
            if (segmentIncrement < segments.size()){
                if (i == segments.get(segmentIncrement).getUpperEndPoint().getYCoords()){
                    double intervalUpperBound = segments.get(segmentIncrement).getUpperEndPoint().getYCoords();
                    double intervalLowerBound = segments.get(segmentIncrement).getLowerEndPoint().getYCoords();
                    System.out.printf("Interval : %.2f %.2f \n", intervalUpperBound, intervalLowerBound);
                    segmentIncrement++;
                }
            }

        }

    }


}
