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
        ArrayList<Intersection> intersections1 = findIntersection("test2.txt");
        System.out.println(intersections1);
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

        System.out.println("-------------------------");
        statusStructure.print("", true);
        ArrayList<Segment> U = p.getSegments(); // recuperation des segments dont p est le point superieur
        ArrayList<Segment> C = statusStructure.segmentsContainingP(p); // recherche dans T des segments contenant p
        ArrayList<Segment> L = new ArrayList<>();

        for (Iterator<Segment> it = C.iterator(); it.hasNext();){ // recherche dans C des segments dont p est le point inferieur
            Segment segment = it.next();
            if (segment.getLowerEndPoint().getXCoords() == p.getXCoords() && segment.getLowerEndPoint().getYCoords() == p.getYCoords()){
                L.add(segment);
                it.remove();
            }
        }

        ArrayList<Segment> CSave = new ArrayList<>(C); // on sauvegarde le C

        System.out.println("------ BEFORE DELETION ------");
        System.out.println("p : " + p);
        System.out.println("U : " + U);
        System.out.println("C : " + C);
        System.out.println("L : " + L);
        statusStructure.print("", true);
//


        if (U.size() + C.size() + L.size() > 1)
            report(new Intersection(p, U, C, L));

        for (Segment segment : C) {
            System.out.println("SEGMENT TO BE DELETED : " + segment);
            statusStructure.suppressStatusStructure(segment, p.getYCoords()); // ToDo : needs to be fixed
        }

        for (Segment segment : L) {
            System.out.println("SEGMENT TO BE DELETED : " + segment);
            statusStructure.suppressStatusStructure(segment, p.getYCoords());
        }
        System.out.println("------ AFTER DELETION ------");
        statusStructure.print("", true);

        // ToDo : faire attention à l'ordre d'insertion : voir pour les segments horizontaux
        for (Segment segment : U) {
            System.out.println("SEGMENT TO BE INSERTED : " + segment);
            statusStructure.insertStatusStructureVariant(segment, p.getYCoords() );
        }
        for (Segment segment : CSave) {
            System.out.println("SEGMENT TO BE INSERTED : " + segment);

            statusStructure.insertStatusStructureVariant(segment, p.getYCoords());
        }
        System.out.println("------ AFTER INSERTION ------");
//        System.out.println("----------------- TREE FOR FINDNEWEVENT");
        statusStructure.print("", true);
//        System.out.println("----------------- ");
        System.out.println(statusStructure.balance());
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
                System.out.println("SPRIME : " + sPrime);
                System.out.println("sL : " + sL);
                if (sL != null)
                    findNewEvent(sL, sPrime, p);
            }
            Segment sPrimePrime = rightmostSegment(p, U, CSave);
            if (sPrimePrime != null){
                Segment sR = statusStructure.getRightNeighbor(sPrimePrime, p);
                System.out.println("SPRIMEPRIME : " + sPrimePrime);
                System.out.println("sR : " + sR);
                if (sR != null)
                    findNewEvent(sPrimePrime, sR, p);
            }
        }

    }

    public static void findNewEvent(Segment sL, Segment sR, EventPoint p){
        EventPoint intersection = intersect(sL, sR);
        System.out.println("INTERSECTION POINT : " + intersection);
        if (intersection != null && !eventQueue.search(intersection) && (intersection.getYCoords() < p.getYCoords() || (intersection.getYCoords() == p.getYCoords() && intersection.getXCoords() > p.getXCoords()))){
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
        double yp = p.getYCoords() - 0.01;
        Segment leftMost = null;
        if (!U.isEmpty()){
            leftMost = U.getFirst();
        } else if (!C.isEmpty()){
            leftMost = C.getFirst();
        }
        for (Segment s : U){
            if (s.xAtYp(yp) < leftMost.xAtYp(yp)){
                leftMost = s;
            }
        }
        for (Segment s : C){
            if (s.xAtYp(yp) < leftMost.xAtYp(yp)){
                leftMost = s;
            }
        }
        return leftMost;
    }

    /**
     * Permet de recuperer le segment le plus a droite de p dans les ensembles C ou U
     * @param p
     * @param U
     * @param C
     * @return
     */
    public static Segment rightmostSegment(EventPoint p, ArrayList<Segment> U, ArrayList<Segment> C){
        double yp = p.getYCoords() - 0.01;
        Segment rightMost = null;
        if (!U.isEmpty()){
            rightMost = U.getFirst();
        } else if (!C.isEmpty()){
            rightMost = C.getFirst();
        }
        for (Segment s : U){
            if (s.xAtYp(yp) > rightMost.xAtYp(yp)){
                rightMost = s;
            }
        }
        for (Segment s : C){
            if (s.xAtYp(yp) > rightMost.xAtYp(yp)){
                rightMost = s;
            }
        }
        return rightMost;
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
//                return new EventPoint((double) Math.round(x0 * 1000) /1000, (double) Math.round(y0 * 1000) /1000);
//                return new EventPoint((double) Math.round(x0 * 100)/100, (double) Math.round(y0 * 100)/100);
                return new EventPoint(x0, y0);
            } else {

                return null; // NON PARA ET NON INTERCEPT
            }
        }

    }
}
