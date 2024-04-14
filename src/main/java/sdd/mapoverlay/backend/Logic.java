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
    public static final double EPSILON = 0.000001d;

    /**
     * Permet de trouver les intersections entre les segments
     * @param segments la liste des segments
     * @return ArrayList<Segment> la liste des points d'intersection
     */
    public static ArrayList<Intersection> findIntersection(ArrayList<Segment> segments){
        eventQueue.initialize(segments);
        while (!eventQueue.isEmpty()){
            EventPoint p = determineNextEvent(eventQueue);
            handleEventPoint(p);
        }
        return intersections;
    }

    /**
     * Permet de determiner le prochain point d'evenement
     * @param eventQueue la EventQueue
     * @return EventPoint le prochain point d'evenement
     */
    public static EventPoint determineNextEvent(EventQueue eventQueue){
        return  eventQueue.suppressMax();
    }

    /**
     * Code principal permettant de traiter un point d'evenement
     * @param p EventPoint le point d'evenement a traiter
     */
    public static void handleEventPoint(EventPoint p){

//        System.out.println("------- NEXT EVENT POINT : " + p + " -------");

        ArrayList<Segment> U = p.getSegments(); // recuperation des segments dont p est le point superieur

        ArrayList<Segment> C = statusStructure.segmentsContainingP(p); // recherche dans T des segments contenant p

        if (p.getIntercepts() != null){
            for (Segment s : p.getIntercepts()){
                if (!C.contains(s)){
                    C.add(s);
                }
            }
        }


        ArrayList<Segment> L = new ArrayList<>();

        for (Iterator<Segment> it = C.iterator(); it.hasNext();){ // recherche dans C des segments dont p est le point inferieur
            Segment segment = it.next();
            if (segment.getLowerEndPoint().getX() == p.getX() && segment.getLowerEndPoint().getY() == p.getY()){
                L.add(segment);
                it.remove();
            }
        }

        ArrayList<Segment> CSave = new ArrayList<>(C); // on sauvegarde le C


        if (U.size() + C.size() + L.size() > 1) {
            report(new Intersection(p, U, C, L));
        }

        ArrayList<Segment> segmentsToBeRemoved = new ArrayList<>();
        if (!C.isEmpty()){
            segmentsToBeRemoved.addAll(C);
        }
        if (!L.isEmpty()){
            segmentsToBeRemoved.addAll(L);
        }

        segmentsToBeRemoved.sort((o1, o2) -> {
            if (o1.xAtYp(p, -0.01) < o2.xAtYp(p,  -0.01)){
                return -1;
            } else if (o1.xAtYp(p, -0.01) > o2.xAtYp(p, -0.01)){
                return 1;
            } else {
                return 0;
            }
        });


//        System.out.println("------- Before suppression -------");
//        System.out.println("Segments to be removed : " + segmentsToBeRemoved);
//        statusStructure.print("", true);

        for (Segment s : segmentsToBeRemoved){
            statusStructure.suppressStatusStructure(s, p);
//            System.out.println("------- After suppression of s : " + s + " -------");
//            statusStructure.print("", true);
        }




        ArrayList<Segment> segmentsToBeInserted = new ArrayList<>();

        if (!U.isEmpty()){
            segmentsToBeInserted.addAll(U);
        }
        if (!CSave.isEmpty()){
            segmentsToBeInserted.addAll(CSave);
        }

        Segment horizontalSegment = null;
        for (Iterator<Segment> it = segmentsToBeInserted.iterator(); it.hasNext();){ // recherche dans C des segments dont p est le point inferieur
            Segment s = it.next();
            if (s.isHorizontal()){
                horizontalSegment = s;
                it.remove();
            }
        }
        segmentsToBeInserted.sort((o1, o2) -> {
            if (o1.xAtYp(p, 0.01) < o2.xAtYp(p, 0.01)){
                return -1;
            } else if (o1.xAtYp(p, 0.01) > o2.xAtYp(p, 0.01)){
                return 1;
            } else {
                return 0;
            }
        });

        if (horizontalSegment != null)
            segmentsToBeInserted.add(horizontalSegment);

//        System.out.println("Segments to be inserted : " + segmentsToBeInserted);

        for (Segment segment : segmentsToBeInserted) {

            statusStructure.insertStatusStructureVariant(segment, p);
//            System.out.println("------- After insertion of s : " + segment + " -------");
//            statusStructure.print("", true);
        }

//        System.out.println("------- After insertion -------");
//        statusStructure.print("", true);
////
//        System.out.println("U : " + U);
//        System.out.println("C : " + C);
//        System.out.println("L : " + L);

        if (U.isEmpty() && CSave.isEmpty()){
            Segment sL = statusStructure.getLeftNeighbor(p);
            Segment sR = statusStructure.getRightNeighbor(p);
//            System.out.println("sL : " + sL);
//            System.out.println("sR : " + sR);
            if (sR != null && sL != null){
//                System.out.println("sL : " + sL);
//                System.out.println("sR : " + sR);
                findNewEvent(sL, sR, p);
            }
        } else {
            Segment sPrime = leftmostSegment(p, U, CSave);
            if (sPrime != null){
//                System.out.println("sPrime : " + sPrime);
                Segment sL = statusStructure.getLeftNeighbor(sPrime, p);
//                System.out.println("sL : " + sL);
                if (sL != null){
                    findNewEvent(sL, sPrime, p);
                }
            }
            Segment sPrimePrime = rightmostSegment(p, U, CSave);
            if (sPrimePrime != null){
//                System.out.println("sPrimePrime : " + sPrimePrime);
                Segment sR = statusStructure.getRightNeighbor(sPrimePrime, p);
//                System.out.println("sR : " + sR);
                if (sR != null) {
                    findNewEvent(sPrimePrime, sR, p);
                }
            }
        }

    }

    /**
     * Permet de trouver un nouvel evenement
     * @param sL le segment le plus a gauche
     * @param sR le segment le plus a droite
     * @param p le point d'intersection
     */
    public static void findNewEvent(Segment sL, Segment sR, EventPoint p){
        EventPoint intersection = intersect(sL, sR);
//        System.out.println("INTERSECTION POINT : " + intersection + " FROM SEGMENTS : " + sL + " AND " + sR);
        if (intersection != null && !eventQueue.search(intersection) && (intersection.getY() < p.getY() || (intersection.getY() == p.getY() && intersection.getX() > p.getX()))){
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
        ArrayList<Segment> segments = new ArrayList<>();
        if (!U.isEmpty()){
            segments.addAll(U);
        }
        if (!C.isEmpty()){
            segments.addAll(C);
        }
        segments.sort((o1, o2) -> {
            if (o1.xAtYp(p, 0.01) < o2.xAtYp(p, 0.01)){
                return -1;
            } else if (o1.xAtYp(p, 0.01) > o2.xAtYp(p, 0.01)){
                return 1;
            } else {
                return 0;
            }
        });
        return segments.get(0);
    }

    /**
     * Permet de recuperer le segment le plus a droite de p dans les ensembles C ou U
     * @param p l'event point
     * @param U l'ensemble dont le point p est le endPoint superieur
     * @param C l'ensemble dont le point p est contenu a l'interieur
     * @return le segment le plus a droite
     */
    public static Segment rightmostSegment(EventPoint p, ArrayList<Segment> U, ArrayList<Segment> C){

        ArrayList<Segment> segments = new ArrayList<>();
        if (!U.isEmpty()){
            segments.addAll(U);
        }
        if (!C.isEmpty()){
            segments.addAll(C);
        }
        segments.sort((o1, o2) -> {
            if (o1.xAtYp(p, 0.01) < o2.xAtYp(p, 0.01)){
                return 1;
            } else if (o1.xAtYp(p, 0.01) > o2.xAtYp(p, 0.01)){
                return -1;
            } else {
                return 0;
            }
        });
        return segments.get(0);
    }


    /**
     * Permet de calculer les coordonnees du point d'intersection de deux segments
     * @param sL
     * @param sR
     * @return
     */
    public static EventPoint intersect(Segment sL, Segment sR){
        double x1 = sL.getLeftEndPoint().getX();
        double y1 = sL.getLeftEndPoint().getY();
        double x2 = sL.getRightEndPoint().getX();
        double y2 = sL.getRightEndPoint().getY();

        double x3 = sR.getLeftEndPoint().getX();
        double y3 = sR.getLeftEndPoint().getY();
        double x4 = sR.getRightEndPoint().getX();
        double y4 = sR.getRightEndPoint().getY();

        double a = (x4 - x3)*(y3 - y1) - (y4 - y3) * (x3 - x1);
        double b = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        double c = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);

        if (b == 0){
            return null;
        } else {
            double alpha = a/b;
            double beta = c/b;
            if ( 0 <= alpha && alpha <= 1 && 0 <= beta && beta <= 1){
                double x0 = x1 + alpha * (x2 - x1);
                double y0 = y1 + alpha * (y2 - y1);

                ArrayList<Segment> intercepts = new ArrayList<>();
                intercepts.add(sL);
                intercepts.add(sR);

                return new EventPoint(x0, y0, EventType.INTERSECTION, intercepts);
            } else {
                return null;
            }
        }

    }
}
