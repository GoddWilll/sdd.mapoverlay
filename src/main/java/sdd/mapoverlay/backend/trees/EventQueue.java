package sdd.mapoverlay.backend.trees;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.base.AVLTree;

import java.util.ArrayList;

public class EventQueue extends AVLTree<EventPoint> {

    public EventQueue(){
        super();
    }

    /**
     * Initialise la Event queue. On insere les EndPoints superieurs avec leur Segment et les EndPoints inferieurs.
     * @param map
     */
    public void initialize(Map map){
        ArrayList<Segment> segments = map.getSegments();
        for (Segment segment : segments){
            if (search(segment.getUpperEndPoint())){
                AVLTree<EventPoint> eq = this;
                while (eq.getData().getX() != segment.getUpperEndPoint().getX() && eq.getData().getY() != segment.getUpperEndPoint().getY()){
                    if (eq.getData().compareTo(segment.getUpperEndPoint()) < 0){
                        eq = eq.getRight();
                    } else if (eq.getData().compareTo(segment.getUpperEndPoint()) > 0){
                        eq = eq.getLeft();
                    }
                }
                eq.getData().addSegment(segment);
                insert(segment.getLowerEndPoint());
            } else {
                insert(segment.getUpperEndPoint());
                insert(segment.getLowerEndPoint());
            }
        }
    }
}
