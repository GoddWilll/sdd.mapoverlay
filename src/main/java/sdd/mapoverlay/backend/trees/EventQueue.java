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
            insert(segment.getUpperEndPoint());
            insert(segment.getLowerEndPoint());
        }
    }
}
