package sdd.mapoverlay.backend;

import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.segments.Intersection;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.trees.EventQueue;
import sdd.mapoverlay.backend.trees.StatusStructure;

import java.util.ArrayList;
import java.util.Iterator;

public class Logic {

    private static StatusStructure statusStructure = new StatusStructure();
    private static EventQueue eventQueue = new EventQueue();
    private static ArrayList<Intersection> intersections = new ArrayList<>();


    public static void main(String[] args){
        ArrayList<Intersection> intersections1 = findIntersection("test.txt");
        System.out.println(intersections1);
//        statusStructure.print("", true);
    }


    public static ArrayList<Intersection> findIntersection(String file){
        Map map = new Map(file);
        eventQueue.initialize(map);
        while (!eventQueue.isEmpty()){
            EventPoint p = determineNextEvent(eventQueue);
            handleEventPoint(p);
        }
        return intersections;
    }

    public static EventPoint determineNextEvent(EventQueue eventQueue){
        return  eventQueue.suppressMax();
    }

    public static void handleEventPoint(EventPoint p){
//        System.out.println("-----------------------");
//        statusStructure.print("", true);
        ArrayList<Segment> U = p.getSegments(); // recuperation des segments dont p est le point superieur
        ArrayList<Segment> C = statusStructure.segmentsContainingP(p); // recherche dans T des segments contenant p
        ArrayList<Segment> L = new ArrayList<>();
//        System.out.println(C);
        for (Iterator<Segment> it = C.iterator(); it.hasNext();){ // recherche dans C des segments dont p est le point inferieur
            Segment segment = it.next();
            if (segment.getLowerEndPoint().getXCoords() == p.getXCoords() && segment.getLowerEndPoint().getYCoords() == p.getYCoords()){
                L.add(segment);
                it.remove();
            }
        }


        ArrayList<Segment> CSave = new ArrayList<>(C); // on sauvegarde le C
//        System.out.println("U : " + U);
//        System.out.println("C : " + C);
//        System.out.println("L : " + L);
        if (U.size() + C.size() + L.size() > 1) // si dans U U C U L on a + d'1 segment
            report(new Intersection(p, U, C, L));

        for (Segment segment : C)
            statusStructure.suppressStatusStructure(segment, p.getYCoords());

        for (Segment segment : L)
            statusStructure.suppressStatusStructure(segment, p.getYCoords());

        // ToDo : faire attention à l'ordre d'insertion
        for (Segment segment : U)
            statusStructure.insertStatusStructureVariant(segment, p.getYCoords());

        for (Segment segment : CSave)
            statusStructure.insertStatusStructureVariant(segment, p.getYCoords());


        if (U.isEmpty() && CSave.isEmpty()){
            Segment sL = statusStructure.getLeftNeighbor(p);
            Segment sR = statusStructure.getRightNeighbor(p);
            if (sR != null && sL != null){
                findNewEvent(sL, sR, p);
            }
        } else {
            Segment sPrime = leftmostSegment(p, U, CSave);
            if (sPrime != null){
                Segment sL = statusStructure.getLeftNeighbor(sPrime, p);
                System.out.println("SL : :" + sL);
                if (sL != null)
                    findNewEvent(sL, sPrime, p);
            }
            Segment sPrimePrime = rightmostSegment(p, U, CSave);
            if (sPrimePrime != null){
                Segment sR = statusStructure.getRightNeighbor(sPrimePrime, p);
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
                return new EventPoint((double) Math.round(x0 * 1000) /1000, (double) Math.round(y0 * 1000) /1000);
            } else {
                return null; // NON PARA ET NON INTERCEPT
            }
        }

    }
}
