package sdd.mapoverlay.backend.utils;

import sdd.mapoverlay.backend.segments.Segment;

import java.util.Comparator;

public class SegmentComparator implements Comparator<Segment> {
    @Override
    public int compare(Segment o1, Segment o2) {
        if (o1.getUpperEndPoint().getY() == o2.getUpperEndPoint().getY()){
            return Double.compare(o1.getUpperEndPoint().getX(), o2.getUpperEndPoint().getX());
        } else {
            return Double.compare(o1.getUpperEndPoint().getY(), o2.getUpperEndPoint().getY());
        }
    }
}
