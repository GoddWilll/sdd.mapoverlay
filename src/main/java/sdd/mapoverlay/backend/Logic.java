package sdd.mapoverlay.backend;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.segments.Intersection;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.EventQueue;
import sdd.mapoverlay.backend.trees.StatusStructure;

import java.util.ArrayList;

public class Logic {

    private static StatusStructure statusStructure = new StatusStructure();
    private static EventQueue eventQueue = new EventQueue();
    private static ArrayList<Intersection> intersections = new ArrayList<>();


    public static void main(String[] args){
        ArrayList<Intersection> intersections1 = findIntersection("fichier1.txt");
        System.out.println(intersections1);
    }


    public static ArrayList<Intersection> findIntersection(String file){
        Map map = new Map(file);
        eventQueue.initialize(map);
//        eventQueue.print("", true);
        while (!eventQueue.isEmpty()){
            EventPoint p = determineNextEvent(eventQueue);
            handleEventPoint(p);
        }
        return intersections;
    }

    public static EventPoint determineNextEvent(EventQueue eventQueue){
        return  eventQueue.suppressMin();
    }

    public static void handleEventPoint(EventPoint p){
//        statusStructure.print("", true);
        ArrayList<Segment> U = p.getSegments(); // recuperation des segments dont p est le point superieur
        ArrayList<Segment> C = statusStructure.segmentsContainingP(p); // recherche dans T des segments contenant p
        ArrayList<Segment> L = new ArrayList<>();

        for (Segment segment : C){ // recherche dans C des segments dont p est le point inferieur
            if (segment.getLowerEndPoint() == p){
                L.add(segment);
                C.remove(segment);
            }
        }
//        System.out.println("U : " + U);
//        System.out.println("C : " + C);
//        System.out.println("L : " + L);


        ArrayList<Segment> CSave = new ArrayList<>(C);

        if (!U.isEmpty() || !L.isEmpty() || !C.isEmpty())
            report(new Intersection(p, U, C, L));

        for (Segment segment : C)
            statusStructure.suppressStatusStructure(segment);

        for (Segment segment : L)
            statusStructure.suppressStatusStructure(segment);

        // ToDo : faire attention à l'ordre d'insertion
        for (Segment segment : U)
            statusStructure.insertStatusStructureVariant(segment);

        for (Segment segment : CSave)
            statusStructure.insertStatusStructureVariant(segment);


        if (U.isEmpty() && CSave.isEmpty()){
            Segment sL = statusStructure.getLeftNeighbor(p);
            Segment sR = statusStructure.getRightNeighbor(p);
            if (sR != null && sL != null){
                findNewEvent(sL, sR, p);
            }
        } else {
            Segment sPrime = leftmostSegment(p, U, CSave);
            if (sPrime != null){
                Segment sL = statusStructure.getLeftNeighbor(sPrime);
                if (sL != null)
                    findNewEvent(sL, sPrime, p);
            }
            Segment sPrimePrime = rightmostSegment(p, U, CSave);
            if (sPrimePrime != null){
                Segment sR = statusStructure.getRightNeighbor(sPrimePrime);
                if (sR != null)
                    findNewEvent(sPrimePrime, sR, p);
            }
        }

    }

    public static void findNewEvent(Segment sL, Segment sR, EventPoint p){
        EventPoint intersection = intersect(sL, sR);
        if (intersection != null && !eventQueue.search(intersection)){
            eventQueue.insert(intersection);
        }
    }

    /**
     * Ajoute l'intersection dans la liste des intersections detectees
     * @param intersection l'objet representant le point d'intersection
     */
    public static void report(Intersection intersection){
        intersections.add(intersection);
    }

    /**
     * Permet de recuperer le segment le plus a gauche de p dans les ensembles C ou U
     * @param p l'event point
     * @param U l'ensemble dont le point p est le endPoint superieur
     * @param C l'ensemble dont le point p est contenu a l'interieur
     * @return le segment le plus a gauche
     */
    public static Segment leftmostSegment(EventPoint p, ArrayList<Segment> U, ArrayList<Segment> C){
        double yp = p.getYCoords();
        double leftMostX = p.getXCoords();
        Segment leftMostSegment = null;
        for (Segment segment : U){
            if (segment.xAtYp(yp) <= leftMostX){
                leftMostX = segment.xAtYp(yp);
                leftMostSegment = segment;
            }
        }
        for (Segment segment : C){
            if (segment.xAtYp(yp) <= leftMostX){
                leftMostX = segment.xAtYp(yp);
                leftMostSegment = segment;
            }
        }
        return leftMostSegment;
    }

    /**
     * Permet de recuperer le segment le plus a droite de p dans les ensembles C ou U
     * @param p
     * @param U
     * @param C
     * @return
     */
    public static Segment rightmostSegment(EventPoint p, ArrayList<Segment> U, ArrayList<Segment> C){
        double yp = p.getYCoords();
        double rightMostX = p.getXCoords();
        Segment rightMostSegment = null;
        for (Segment segment : U){
            if (segment.xAtYp(yp) >= rightMostX){
                rightMostX = segment.xAtYp(yp);
                rightMostSegment = segment;
            }
        }
        for (Segment segment : C){
            if (segment.xAtYp(yp) >= rightMostX){
                rightMostX = segment.xAtYp(yp);
                rightMostSegment = segment;
            }
        }
        return rightMostSegment;
    }


    /**
     * Permet de calculer les coordonnees du point d'intersection de deux segments
     * @param sL
     * @param sR
     * @return
     */
    public static EventPoint intersect(Segment sL, Segment sR){
        double x1 = sL.getLeftEndPoint().getXCoords();
        double y1 = sL.getLeftEndPoint().getYCoords();
        double x2 = sL.getRightEndPoint().getXCoords();
        double y2 = sL.getRightEndPoint().getYCoords();

        double x3 = sR.getLeftEndPoint().getXCoords();
        double y3 = sR.getLeftEndPoint().getYCoords();
        double x4 = sR.getRightEndPoint().getXCoords();
        double y4 = sR.getRightEndPoint().getYCoords();

        double a = (x4 - x3)*(y3 - y1) - (y4 - y3) * (x3 - x1);
        double b = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        double c = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);

        if (b == 0){
            if (a == 0){
                return null; // CONFONDUS !!
            } else {
                return null; // PARALLELES !!
            }
        } else {
            double alpha = a/b;
            double beta = c/b;
            if ( 0 <= alpha && alpha <= 1 && 0 <= beta && beta <= 1){
                double x0 = x1 + alpha * (x2 - x1);
                double y0 = y1 + alpha * (y2 - y1);
                return new EventPoint(x0, y0);
            } else {
                return null; // NON PARA ET NON INTERCEPT
            }
        }

    }
}
