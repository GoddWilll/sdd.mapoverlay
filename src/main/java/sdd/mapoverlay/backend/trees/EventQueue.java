package sdd.mapoverlay.backend.trees;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.base.AVLTree;

import java.util.ArrayList;

/**
 * Classe representant la EventQueue
 */
public class EventQueue extends AVLTree<EventPoint> {

    public EventQueue() {
        super();
    }

    /**
     * Permet d'initialiser la EventQueue avec une liste de segments
     * @param segments ArrayList la liste de segments
     */
    public void initialize(ArrayList<Segment> segments) {
        for (Segment segment : segments) {
            if (search(segment.getUpperEndPoint())) {
                AVLTree<EventPoint> eq = this;
                while (eq.getData().getX() != segment.getUpperEndPoint().getX()
                        && eq.getData().getY() != segment.getUpperEndPoint().getY()) {
                    if (eq.getData().compareTo(segment.getUpperEndPoint()) < 0) {
                        eq = eq.getRight();
                    } else if (eq.getData().compareTo(segment.getUpperEndPoint()) > 0) {
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
