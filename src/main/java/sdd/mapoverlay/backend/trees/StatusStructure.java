package sdd.mapoverlay.backend.trees;

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.trees.base.AVLTree;
import sdd.mapoverlay.backend.trees.base.BSTree;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

public class StatusStructure extends AVLTree<Segment>  {

    final Boolean isStatus = true;

    public StatusStructure(){
        super();
    }

    public Boolean getIsStatus(){
        return isStatus;
    }

    public ArrayList<Segment> containsP(EventPoint p){
        return null;
    }

}
