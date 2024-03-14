package sdd.mapoverlay.backend.trees;

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.trees.base.AVLTree;
import sdd.mapoverlay.backend.trees.base.BSTree;

public class StatusStructure<EventPoint extends Comparable> extends AVLTree<EventPoint> {

    final Boolean isStatus = true;

    public StatusStructure(){
        super();
    }

    public Boolean getIsStatus(){
        return isStatus;
    }

}
