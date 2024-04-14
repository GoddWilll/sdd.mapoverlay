package sdd.mapoverlay.backend.trees;

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.base.AVLTree;

import java.util.ArrayList;

/**
 * Represents an event queue, which is a specialized AVL tree that stores event
 * points.
 * The event queue is used in the map overlay algorithm to handle events during
 * the sweep line process.
 */
public class EventQueue extends AVLTree<EventPoint> {

    /**
     * Constructs an empty event queue.
     */
    public EventQueue() {
        super();
    }

    /**
     * Initializes the event queue with a list of segments.
     * This method populates the event queue with event points and segments.
     *
     * @param segments the list of segments to initialize the event queue with
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
