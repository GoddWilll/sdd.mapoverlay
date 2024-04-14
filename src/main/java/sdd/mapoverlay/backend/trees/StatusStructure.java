package sdd.mapoverlay.backend.trees;

import sdd.mapoverlay.backend.Logic;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.trees.base.AVLTree;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

/*
 * Represents the status structure, which is a specialized AVL tree that stores
 * segments.
 * The status structure is used in the map overlay algorithm to store the
 */
public class StatusStructure extends AVLTree<Segment> {

    /**
     * Constructs an empty status structure.
     */
    public StatusStructure() {
        super();
    }

    /**
     * Initializes the status structure with a list of segments.
     * This method populates the status structure with segments.
     *
     * @param segments the list of segments to initialize the status structure with
     */
    public ArrayList<Segment> segmentsContainingP(EventPoint p) {
        ArrayList<Segment> segments = new ArrayList<>();

        AVLTree<Segment> tree = this;

        while (tree.height() > 1) {
            if (tree.getData().xAtYp(p) < p.getX()) {
                tree = tree.getRight();
            } else {
                tree = tree.getLeft();
            }
        }

        if (tree.height() == 0) {
            return segments;
        }

        if (tree.getData().containsPoint(p)) {
            // ajout du segment a la liste des segments
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

    /**
     * Retourne la liste des segments voisins gauche de p dans T
     * 
     * @param p EventPoint le point
     * @return ArrayList la liste des segments voisins gauche de p
     */
    public Segment getLeftNeighbor(EventPoint p) {
        double yp = p.getY();
        AVLTree<Segment> tree = this;
        if (tree.isEmpty()) {
            return null;
        } else {
            while (tree.height() > 1) {
                if (tree.getData().xAtYp(p) < p.getX()) {
                } else {
                    tree = tree.getLeft();
                }
            }
            if (tree.getData().xAtYp(p) < p.getX()) {
                return tree.getData();
            } else {
                if (tree.getFather() == null) {
                    return null;
                }
                if (tree.getFather().getRight() == tree) {
                    tree = tree.getFather().getLeft();
                    while (!tree.getRight().isEmpty()) {
                        tree = tree.getRight();
                    }
                    return tree.getData();
                } else {
                    if (tree.getFather().getFather() == null) {
                        return null;
                    } else {
                        while (tree.getFather().getFather() != null && tree.getFather().getLeft() == tree) {
                            tree = tree.getFather();
                        }
                        tree = tree.getFather().getLeft();
                        while (!tree.getRight().isEmpty()) {
                            tree = tree.getRight();
                        }
                        return tree.getData();
                    }
                }
            }
        }
    }

    public Segment getRightNeighbor(EventPoint p) {
        double yp = p.getY();
        AVLTree<Segment> tree = this;
        if (tree.isEmpty()) {
            return null;
        } else {
            while (tree.height() > 1) {
                if (tree.getData().xAtYp(p) < p.getX()) {
                    tree = tree.getRight();
                } else {
                    tree = tree.getLeft();
                }
            }
            if (tree.getData().xAtYp(p) > p.getX()) {
                return tree.getData();
            } else {
                if (tree.getFather() == null) {
                    return null;
                }
                if (tree.getFather().getLeft() == tree) {
                    tree = tree.getFather().getRight();
                    while (!tree.getLeft().isEmpty()) {
                        tree = tree.getLeft();
                    }
                    return tree.getData();
                } else {
                    if (tree.getFather().getFather() == null) {
                        return null;
                    } else {
                        while (tree.getFather().getFather() != null && tree.getFather().getRight() == tree) {
                            tree = tree.getFather();
                        }
                        tree = tree.getFather().getRight();
                        while (!tree.getLeft().isEmpty()) {
                            tree = tree.getLeft();
                        }
                        return tree.getData();
                    }
                }
            }
        }
    }

    /**
     * Retourne le segment voisin gauche de s dans la StatusStructure
     * 
     * @param s Segment le segment
     * @param p EventPoint le point
     * @return Segment le segment voisin gauche de s
     */
    public Segment getLeftNeighbor(Segment s, EventPoint p) {
        AVLTree<Segment> tree = this;
        while (tree.height() > 1) {
            if (Math.abs(tree.getData().xAtYp(p) - s.xAtYp(p)) < Logic.EPSILON) {
                tree = tree.getLeft();
            } else if (tree.getData().xAtYp(p) < s.xAtYp(p)) {
                tree = tree.getRight();
            } else if (tree.getData().xAtYp(p) > s.xAtYp(p)) {
                tree = tree.getLeft();
            }
        }
        if (tree.getData() == null || tree.getFather() == null) {
            return null;
        }

        // On est dans le segment le plus a droite, on recupere maintenant le voisin
        // droit
        if (tree.getFather().getRight() == tree) { // si on est dans le fils gauche
            tree = tree.getFather().getLeft(); // on va dans le fils droit
            while (!tree.getRight().isEmpty()) { // on va tout a gauche pour arriver dans une feuille
                tree = tree.getRight();
            }
            return tree.getData(); // on retourne le segment de la feuille
        } else if (tree.getFather().getLeft() == tree) {
            if (tree.getFather().getFather() == null) {
                return null; // pas de voisin droit
            } else {
                while (tree.getFather().getFather() != null && tree.getFather().getLeft() == tree) {
                    tree = tree.getFather();
                }
                // On atteint le pere avant la racine de l'arbre
                if (tree.getFather().getLeft() == tree) {
                    return null;
                }

                tree = tree.getFather().getLeft();
                while (!tree.getRight().isEmpty()) {
                    tree = tree.getRight();
                }
                return tree.getData();
            }
        }
        return null;
    }

    /**
     * Retourne le segment voisin droit de s dans T
     * 
     * @param s Segment le plus a droite
     * @param p EventPoint le point
     * @return Segment le segment voisin droit de s
     */
    public Segment getRightNeighbor(Segment s, EventPoint p) {
        double yp = p.getY();
        AVLTree<Segment> tree = this;
        while (tree.height() > 1) {
            if (Math.abs(tree.getData().xAtYp(p) - s.xAtYp(p)) < Logic.EPSILON) {
                if (tree.getData().isSameSegment(s)) {
                    tree = tree.getLeft();
                } else {
                    tree = tree.getRight();
                }
            } else if (tree.getData().xAtYp(p) < s.xAtYp(p)) {
                tree = tree.getRight();
            } else if (tree.getData().xAtYp(p) > s.xAtYp(p)) {
                tree = tree.getLeft();
            }
        }
        if (tree.getData() == null || tree.getFather() == null) {
            return null;
        }
        // On est dans le segment le plus a droite, on recupere maintenant le voisin
        // droit
        if (tree.getFather().getLeft() == tree) { // si on est dans le fils gauche
            tree = tree.getFather().getRight(); // on va dans le fils droit
            while (!tree.getLeft().isEmpty()) { // on va tout a gauche pour arriver dans une feuille
                tree = tree.getLeft();
            }
            return tree.getData(); // on retourne le segment de la feuille
        } else {
            // on est dans le fils droit
            if (tree.getFather().getFather() == null) {
                return null; // pas de voisin droit
            } else {
                while (tree.getFather().getFather() != null && tree.getFather().getRight() == tree) {
                    tree = tree.getFather();
                }

                if (tree.getFather().getRight() == tree) {
                    return null;
                }

                // On est dans un fils gauche maintenant
                tree = tree.getFather().getRight();
                while (!tree.getLeft().isEmpty()) {
                    tree = tree.getLeft();
                }
                return tree.getData();
            }
        }
    }

    @Override
    public String toString() {
        return "" + getData();
    }

}
