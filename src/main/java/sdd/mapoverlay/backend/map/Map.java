package sdd.mapoverlay.backend.map;

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.points.types.Position;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.utils.MapLoader;
import sdd.mapoverlay.backend.utils.SegmentComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.control.Alert;

public class Map {
    private ArrayList<Segment> segments = new ArrayList<>();
    // private double maxYPosition;
    // private double maxXPosition;
    // private double minYPosition;
    // private double minXPosition;

    /**
     * Constructeur de la classe Map, representant la carte du plam
     * 
     * @param fileName un String contenant le nom du fichier a charger
     */
    public Map(String fileName) {
        fileInitialization(fileName);
    }

    /**
     * Methode permettant d'initialiser la carte a partir d'un fichier
     * 
     * @param fileName un String contenant le nom du fichier a charger
     */
    public void fileInitialization(String fileName) {

        try {
            MapLoader mapLoader = new MapLoader(fileName);
            ArrayList<String> mapData = mapLoader.getMap();
            EventPoint upperEndPoint;
            EventPoint lowerEndPoint;

            for (String mapDatum : mapData) {
                String[] strCoordinates = mapDatum.split(" ");
                Double[] coordinates = new Double[strCoordinates.length];
                for (int j = 0; j < coordinates.length; j++) {
                    coordinates[j] = Double.parseDouble(strCoordinates[j]);
                }

                if (coordinates[1] < coordinates[3]) { // y1 < y2
                    if (coordinates[0] <= coordinates[2]) { // x1 < x2
                        lowerEndPoint = new EventPoint(coordinates[0], coordinates[1], EventType.LOWERENDPOINT,
                                Position.LEFT);
                        upperEndPoint = new EventPoint(coordinates[2], coordinates[3], EventType.UPPERENDPOINT,
                                Position.RIGHT);
                    } else { // x1 > x2
                        lowerEndPoint = new EventPoint(coordinates[0], coordinates[1], EventType.LOWERENDPOINT,
                                Position.RIGHT);
                        upperEndPoint = new EventPoint(coordinates[2], coordinates[3], EventType.UPPERENDPOINT,
                                Position.LEFT);
                    }

                } else { // y1 >= y2
                    if (coordinates[1].equals(coordinates[3])) { // y1 == y2
                        if (coordinates[0] < coordinates[2]) { // si x1 < x2
                            upperEndPoint = new EventPoint(coordinates[0], coordinates[1], EventType.UPPERENDPOINT,
                                    Position.LEFT);
                            lowerEndPoint = new EventPoint(coordinates[2], coordinates[3], EventType.LOWERENDPOINT,
                                    Position.RIGHT);
                        } else { // si x1 > x2
                            lowerEndPoint = new EventPoint(coordinates[0], coordinates[1], EventType.LOWERENDPOINT,
                                    Position.RIGHT);
                            upperEndPoint = new EventPoint(coordinates[2], coordinates[3], EventType.UPPERENDPOINT,
                                    Position.LEFT);
                        }
                    } else { // si y1 > y2
                        if (coordinates[0] < coordinates[2]) { // si x1 < x2
                            upperEndPoint = new EventPoint(coordinates[0], coordinates[1], EventType.UPPERENDPOINT,
                                    Position.LEFT);
                            lowerEndPoint = new EventPoint(coordinates[2], coordinates[3], EventType.LOWERENDPOINT,
                                    Position.RIGHT);
                        } else { // si x1 > x2
                            upperEndPoint = new EventPoint(coordinates[0], coordinates[1], EventType.UPPERENDPOINT,
                                    Position.RIGHT);
                            lowerEndPoint = new EventPoint(coordinates[2], coordinates[3], EventType.LOWERENDPOINT,
                                    Position.LEFT);
                        }
                    }
                }
                // Section permettant d'ajouter le segment au endpoint superieur pour stockage
                // dans eventQueue
                Segment upperPointSegment = new Segment(upperEndPoint, lowerEndPoint);
                upperEndPoint.addSegment(upperPointSegment);

                // Creation du segment et ajout a la liste de segments
                segments.add(new Segment(upperEndPoint, lowerEndPoint));

            }
        } catch (Exception e) {
            // Handle the exception here
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading map file please select a valid file");
        }

    }

    /**
     * Permet de retourner la liste des segments de la carte apres lecture du
     * fichier
     * 
     * @return un ArrayList de Segment
     */
    public ArrayList<Segment> getSegments() {
        return segments;
    }

    // public double getMaxYPosition(){
    // return maxYPosition;
    // }
    //
    // public double getMaxXPosition(){
    // return maxXPosition;
    // }
    //
    // public double getMinXPosition(){
    // return minXPosition;
    // }
    //
    // public double getMinYPosition(){
    // return minYPosition;
    // }
    //
    // private void updateMaxXPosition(double x){
    // if (x > maxXPosition) {
    // this.maxXPosition = x;
    // }
    // }
    //
    // private void updateMaxYPosition(double y){
    // if (y > maxYPosition){
    // this.maxYPosition = y;
    // }
    // }
    //
    // private void updateMinXPosition(double x){
    // if (x < minXPosition){
    // this.minXPosition = x;
    // }
    // }
    //
    // private void updateMinYPosition(double y){
    // if (y < minYPosition){
    // this.minYPosition = y;
    // }
    // }
}
