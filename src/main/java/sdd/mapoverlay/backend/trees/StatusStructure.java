package sdd.mapoverlay.backend.trees;

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.Position;
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

//    public ArrayList<Segment> segmentsContainingP(EventPoint p){
//        ArrayList<Segment> segments = new ArrayList<>();
//        AVLTree<Segment> tree = this;
//        while (tree.height() > 1){
//            if (tree.getData().pos(p) == Position.LEFT){
//                tree = tree.getLeft();
//            } else if (tree.getData().pos(p) == Position.RIGHT){
//                tree = tree.getRight();
//            } else {
//                tree = tree.getLeft();
//            }
//        }
//        System.out.println(tree);
//        if (tree.getData().containsPoint(p)){
//            segments.add(tree.getData());
//        }
//        ArrayList<Segment> left = tree.getLeftNeighbors(p);
//        ArrayList<Segment> right = tree.getRightNeighbors(p);
//        if (left.size() > 0)
//            segments.addAll(left);
//        if (right.size() > 0)
//            segments.addAll(right);
//
//        return segments;
//    }

    public ArrayList<Segment> segmentsContainingP(EventPoint p){

        ArrayList<Segment> segments = new ArrayList<>();
        AVLTree<Segment> tree = this;

        while (tree.height() > 1){
            if (tree.getData().xAtYp(p.getYCoords()) < p.getXCoords()){
                tree = tree.getLeft();
            } else if (tree.getData().xAtYp(p.getYCoords()) > p.getXCoords()){
                tree = tree.getRight();
            } else {
                tree = tree.getLeft();
            }
        }

        if (tree.height() == 0){
            return segments;
        }

        if (tree.getData().containsPoint(p)){
            segments.add(tree.getData());
        }
        ArrayList<Segment> left = tree.getLeftNeighbors(p);
        ArrayList<Segment> right = tree.getRightNeighbors(p);
        if (left.size() > 0)
            segments.addAll(left);
        if (right.size() > 0)
            segments.addAll(right);

        return segments;
    }
//    public Segment getLeftNeighbor(EventPoint p){
//        AVLTree<Segment> tree = this;
//        while (tree.height() > 1){
//            if (tree.getData().pos(p) == Position.LEFT){
//                tree = tree.getLeft();
//            } else if (tree.getData().pos(p) == Position.RIGHT){
//                tree = tree.getRight();
//            } else {
//                tree = tree.getLeft();
//            }
//        }
//        if (tree.height() == 0){
//            return null;
//        }
//        if (tree.getData().pos(p) == Position.LEFT){
//            return tree.getData();
//        } else {
//            AVLTree<Segment> startingLeaf = tree;
//            if (startingLeaf.getFather().getRight() == startingLeaf){ // on part d'un fils droit
//                AVLTree<Segment> currentTree = startingLeaf; // on va dans le pere vu qu'on est un fils droit
//                currentTree = currentTree.getFather().getLeft();
//                if (currentTree.getRight().getData() != null){
//                    currentTree = currentTree.getRight();
//                }
//                return currentTree.getData();
//            } else if (startingLeaf.getFather().getLeft() == startingLeaf){
//                AVLTree<Segment> currentTree = startingLeaf;
//                if (currentTree.getFather().getFather() == null) {
//                    return null;
//                }
//                while(currentTree.getFather() != null && currentTree.getFather().getLeft() == currentTree){
//                    currentTree = currentTree.getFather();
//                }
//                if (currentTree.getFather() == null){
//                    return null;
//                }
//                currentTree = currentTree.getFather().getLeft();
//                while (currentTree.getRight().getData() != null){
//                    currentTree = currentTree.getRight();
//                }
//                return currentTree.getData();
//            }
//        }
//        return null;
//    }

    public Segment getLeftNeighbor(EventPoint p){
        AVLTree<Segment> tree = this;
        while (tree.height() > 1){
            if (tree.getData().xAtYp(p.getYCoords()) < p.getXCoords()){
                tree = tree.getLeft();
            } else if (tree.getData().xAtYp(p.getYCoords()) > p.getXCoords()){
                tree = tree.getRight();
            } else {
                tree = tree.getLeft();
            }
        }
        if (tree.height() == 0){
            return null;
        }
        if (tree.getData().xAtYp(p.getYCoords()) < p.getXCoords()){
            return tree.getData();
        } else {
            if (tree.getFather() == null)
                return null;
            AVLTree<Segment> startingLeaf = tree;
            if (startingLeaf.getFather().getRight() == startingLeaf){ // on part d'un fils droit
                AVLTree<Segment> currentTree = startingLeaf; // on va dans le pere vu qu'on est un fils droit
                currentTree = currentTree.getFather().getLeft();
                if (currentTree.getRight().getData() != null){
                    currentTree = currentTree.getRight();
                }
                return currentTree.getData();
            } else if (startingLeaf.getFather().getLeft() == startingLeaf){
                AVLTree<Segment> currentTree = startingLeaf;
                if (currentTree.getFather().getFather() == null) {
                    return null;
                }
                while(currentTree.getFather() != null && currentTree.getFather().getLeft() == currentTree){
                    currentTree = currentTree.getFather();
                }
                if (currentTree.getFather() == null){
                    return null;
                }
                currentTree = currentTree.getFather().getLeft();
                while (currentTree.getRight().getData() != null){
                    currentTree = currentTree.getRight();
                }
                return currentTree.getData();
            }
        }
        return null;
    }

//    public Segment getRightNeighbor(EventPoint p){
//        AVLTree<Segment> tree = this;
//        while (tree.height() > 1){
//            if (tree.getData().pos(p) == Position.LEFT){
//                tree = tree.getLeft();
//            } else if (tree.getData().pos(p) == Position.RIGHT){
//                tree = tree.getRight();
//            } else {
//                tree = tree.getLeft();
//            }
//        } // on est dans un segment avec p
//
//        if (tree.height() == 0){
//            return null;
//        }
//        if (tree.getData().pos(p) == Position.RIGHT){
//            return tree.getData();
//        } else {
//            AVLTree<Segment> startingLeaf = tree;
//            // si on est dans une feuille gauche
//            if (startingLeaf.getFather().getLeft() == startingLeaf) {
//                AVLTree<Segment> currentTree = startingLeaf;
//                currentTree = currentTree.getFather().getRight();
//                if (currentTree.getData() == null)
//                    return null;
//                if (currentTree.getLeft().getData() != null) {
//                    currentTree = currentTree.getLeft();
//                }
//                return currentTree.getData();
//            } else if (startingLeaf.getFather().getRight() == startingLeaf) { // si deja dans une feuille droite
//                AVLTree<Segment> currentTree = startingLeaf;
//                if (currentTree.getFather().getFather() == null) {
//                    return null;
//                }
//                while (currentTree.getFather() != null && currentTree.getFather().getRight() == currentTree) {
//                    currentTree = currentTree.getFather();
//                }
//                if (currentTree.getFather() == null) {
//                    return null;
//                }
//                currentTree = currentTree.getFather().getRight();
//                while (currentTree.getLeft().getData() != null) {
//                    currentTree = currentTree.getLeft();
//                }
//               return currentTree.getData();
//            }
//        }
//        return null;
//    }


    public Segment getRightNeighbor(EventPoint p){
        AVLTree<Segment> tree = this;
        double yp = p.getYCoords();
        while (tree.height() > 1){
            if (tree.getData().xAtYp(yp) < p.getXCoords()){
                tree = tree.getLeft();
            } else if (tree.getData().xAtYp(yp) > p.getXCoords()){
                tree = tree.getRight();
            } else {
                tree = tree.getLeft();
            }
        } // on est dans un segment avec p

        if (tree.height() == 0){
            return null;
        }
        if (tree.getData().xAtYp(yp) > p.getXCoords()){
            return tree.getData();
        } else {
            AVLTree<Segment> startingLeaf = tree;
            // si on est dans une feuille gauche
            if (startingLeaf.getFather().getLeft() == startingLeaf) {
                AVLTree<Segment> currentTree = startingLeaf;
                currentTree = currentTree.getFather().getRight();
                if (currentTree.getData() == null)
                    return null;
                if (currentTree.getLeft().getData() != null) {
                    currentTree = currentTree.getLeft();
                }
                return currentTree.getData();
            } else if (startingLeaf.getFather().getRight() == startingLeaf) { // si deja dans une feuille droite
                AVLTree<Segment> currentTree = startingLeaf;
                if (currentTree.getFather().getFather() == null) {
                    return null;
                }
                while (currentTree.getFather() != null && currentTree.getFather().getRight() == currentTree) {
                    currentTree = currentTree.getFather();
                }
                if (currentTree.getFather() == null) {
                    return null;
                }
                currentTree = currentTree.getFather().getRight();
                while (currentTree.getLeft().getData() != null) {
                    currentTree = currentTree.getLeft();
                }
                return currentTree.getData();
            }
        }
        return null;
    }
//    public Segment getLeftNeighbor(Segment s){
//        AVLTree<Segment> tree = this;
//        while (tree.height() > 1){
////            System.out.println(s);
//            if (tree.getData().determinePosition(s) == Position.LEFT){
//                tree = tree.getLeft();
//            } else if (tree.getData().determinePosition(s) == Position.RIGHT){
//                tree = tree.getRight();
//            } else {
//                tree = tree.getLeft();
//            }
//        }
//        if (tree.getData() == null){
//            return null;
//        }
//        if (tree.getData().determinePosition(s) == Position.LEFT){
//            return tree.getData();
//        } else {
//            AVLTree<Segment> startingLeaf = tree;
//            if (startingLeaf.getFather().getRight() == startingLeaf){ // on part d'un fils droit
//                AVLTree<Segment> currentTree = startingLeaf; // on va dans le pere vu qu'on est un fils droit
//                currentTree = currentTree.getFather().getLeft();
//                if (currentTree.getData() == null)
//                    return null;
//                if (currentTree.getRight().getData() != null){
//                    currentTree = currentTree.getRight();
//                }
//                return currentTree.getData();
//            } else if (startingLeaf.getFather().getLeft() == startingLeaf){
//                AVLTree<Segment> currentTree = startingLeaf;
//                if (currentTree.getFather().getFather() == null) {
//                    return null;
//                }
//                while(currentTree.getFather() != null && currentTree.getFather().getLeft() == currentTree){
//                    currentTree = currentTree.getFather();
//                }
//                if (currentTree.getFather() == null){
//                    return null;
//                }
//                currentTree = currentTree.getFather().getLeft();
//                while (currentTree.getRight().getData() != null){
//                    currentTree = currentTree.getRight();
//                }
//                return currentTree.getData();
//            }
//        }
//        return null;
//    }
//
//    public Segment getRightNeighbor(Segment s){
//        AVLTree<Segment> tree = this;
//        while (tree.height() > 1){
//            if (tree.getData().determinePosition(s) == Position.LEFT){
//                tree = tree.getLeft();
//            } else if (tree.getData().determinePosition(s) == Position.RIGHT){
//                tree = tree.getRight();
//            } else {
//                tree = tree.getLeft();
//            }
//        } // on est dans un segment avec p
//        if (tree.getData() == null)
//            return null;
//        if (tree.getData().determinePosition(s) == Position.RIGHT){
//            return tree.getData();
//        } else {
//            AVLTree<Segment> startingLeaf = tree;
//            // si on est dans une feuille gauche
//            if (startingLeaf.getFather().getLeft() == startingLeaf) {
//                AVLTree<Segment> currentTree = startingLeaf;
//                currentTree = currentTree.getFather().getRight();
//                if (currentTree.getData() == null)
//                    return null;
//                if (currentTree.getLeft().getData() != null) {
//                    currentTree = currentTree.getLeft();
//                }
//                return currentTree.getData();
//            } else if (startingLeaf.getFather().getRight() == startingLeaf) { // si deja dans une feuille droite
//                AVLTree<Segment> currentTree = startingLeaf;
//                if (currentTree.getFather().getFather() == null) {
//                    return null;
//                }
//                while (currentTree.getFather() != null && currentTree.getFather().getRight() == currentTree) {
//                    currentTree = currentTree.getFather();
//                }
//                if (currentTree.getFather() == null) {
//                    return null;
//                }
//                currentTree = currentTree.getFather().getRight();
//                while (currentTree.getLeft().getData() != null) {
//                    currentTree = currentTree.getLeft();
//                }
//                return currentTree.getData();
//            }
//        }
//        return null;
//    }


    public Segment getLeftNeighbor(Segment s, EventPoint p ){
        System.out.println("------------------");
        print("", true);
        System.out.println("S : " + s);
        double yp = p.getYCoords();
        AVLTree<Segment> tree = this;
        while (tree.height() > 1){
//            System.out.println(s);
            if (tree.getData().xAtYp(yp) < s.xAtYp(yp) ){
                tree = tree.getLeft();
            } else if (tree.getData().xAtYp(yp) > s.xAtYp(yp)){
                tree = tree.getRight();
            } else {
                tree = tree.getLeft();
            }
        }
        if (tree.getData() == null){
            return null;
        }
        if (tree.getData().xAtYp(yp) < s.xAtYp(yp)){
            return tree.getData();
        } else {
            AVLTree<Segment> startingLeaf = tree;
            if (startingLeaf.getFather().getRight() == startingLeaf){ // on part d'un fils droit
                AVLTree<Segment> currentTree = startingLeaf; // on va dans le pere vu qu'on est un fils droit
                currentTree = currentTree.getFather().getLeft();
                if (currentTree.getData() == null)
                    return null;
                if (currentTree.getRight().getData() != null){
                    currentTree = currentTree.getRight();
                }
                return currentTree.getData();
            } else if (startingLeaf.getFather().getLeft() == startingLeaf){
                AVLTree<Segment> currentTree = startingLeaf;
                if (currentTree.getFather().getFather() == null) {
                    return null;
                }
                while(currentTree.getFather() != null && currentTree.getFather().getLeft() == currentTree){
                    currentTree = currentTree.getFather();
                }
                if (currentTree.getFather() == null){
                    return null;
                }
                currentTree = currentTree.getFather().getLeft();
                while (currentTree.getRight().getData() != null){
                    currentTree = currentTree.getRight();
                }
                return currentTree.getData();
            }
        }
        return null;
    }

    public Segment getRightNeighbor(Segment s, EventPoint p){
        AVLTree<Segment> tree = this;
        double yp = p.getYCoords();
        while (tree.height() > 1){
            if (tree.getData().xAtYp(yp) < s.xAtYp(yp)){
                tree = tree.getLeft();
            } else if (tree.getData().xAtYp(yp) > s.xAtYp(yp)){
                tree = tree.getRight();
            } else {
                tree = tree.getLeft();
            }
        } // on est dans un segment avec p
        if (tree.getData() == null)
            return null;
        if (tree.getData().xAtYp(yp) > s.xAtYp(yp)){
            return tree.getData();
        } else {
            AVLTree<Segment> startingLeaf = tree;
            // si on est dans une feuille gauche
            if (startingLeaf.getFather().getLeft() == startingLeaf) {
                AVLTree<Segment> currentTree = startingLeaf;
                currentTree = currentTree.getFather().getRight();
                if (currentTree.getData() == null)
                    return null;
                if (currentTree.getLeft().getData() != null) {
                    currentTree = currentTree.getLeft();
                }
                return currentTree.getData();
            } else if (startingLeaf.getFather().getRight() == startingLeaf) { // si deja dans une feuille droite
                AVLTree<Segment> currentTree = startingLeaf;
                if (currentTree.getFather().getFather() == null) {
                    return null;
                }
                while (currentTree.getFather() != null && currentTree.getFather().getRight() == currentTree) {
                    currentTree = currentTree.getFather();
                }
                if (currentTree.getFather() == null) {
                    return null;
                }
                currentTree = currentTree.getFather().getRight();
                while (currentTree.getLeft().getData() != null) {
                    currentTree = currentTree.getLeft();
                }
                return currentTree.getData();
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return "" + getData();
    }

}
