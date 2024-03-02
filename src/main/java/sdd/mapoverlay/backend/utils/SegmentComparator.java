package sdd.mapoverlay.backend.utils;

import sdd.mapoverlay.backend.segments.Segment;

import java.util.Comparator;

public class SegmentComparator implements Comparator<Segment> {
    @Override
    public int compare(Segment o1, Segment o2) {
        if (o1.getUpperEndPoint().getYCoords() == o2.getUpperEndPoint().getYCoords()){
            return Double.compare(o1.getUpperEndPoint().getXCoords(), o2.getUpperEndPoint().getXCoords());
        } else {
            return Double.compare(o1.getUpperEndPoint().getYCoords(), o2.getUpperEndPoint().getYCoords());
        }
    }
}
