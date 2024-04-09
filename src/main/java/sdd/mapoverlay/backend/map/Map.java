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

public class Map {
    private ArrayList<Segment> segments = new ArrayList<>();
    private double maxYPosition;
    private double maxXPosition;
    private double minYPosition;
    private double minXPosition;

    public Map(String fileName) {
        fileInitialization(fileName);
    }

    public void fileInitialization(String fileName) {

        MapLoader mapLoader = new MapLoader(fileName);
        ArrayList<String> mapData = mapLoader.getMap();
        EventPoint upperEndPoint;
        EventPoint lowerEndPoint;

        maxXPosition = 0;
        maxYPosition = 0;
        minXPosition = 0;
        minYPosition = 0;

        for (String mapDatum : mapData) {
            String[] strCoordinates = mapDatum.split(" ");
            Double[] coordinates = new Double[strCoordinates.length];
            for (int j = 0; j < coordinates.length; j++) {
                coordinates[j] = Double.parseDouble(strCoordinates[j]);
            }

            for (Double coordinate : coordinates) {
                updateMaxXPosition(coordinate);
                updateMaxYPosition(coordinate);
                updateMinXPosition(coordinate);
                updateMinYPosition(coordinate);
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

    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    // public ArrayList<Segment> getVerticallySortedSegments(){
    // Comparator<? super Segment> SegmentComparator = new SegmentComparator();
    // segments.sort(SegmentComparator);
    // Collections.reverse(segments);
    // return segments;
    // }

    public double getMaxYPosition() {
        return maxYPosition;
    }

    public double getMaxXPosition() {
        return maxXPosition;
    }

    public double getMinXPosition() {
        return minXPosition;
    }

    public double getMinYPosition() {
        return minYPosition;
    }

    private void updateMaxXPosition(double x) {
        if (x > maxXPosition) {
            this.maxXPosition = x;
        }
    }

    private void updateMaxYPosition(double y) {
        if (y > maxYPosition) {
            this.maxYPosition = y;
        }
    }

    private void updateMinXPosition(double x) {
        if (x < minXPosition) {
            this.minXPosition = x;
        }
    }

    private void updateMinYPosition(double y) {
        if (y < minYPosition) {
            this.minYPosition = y;
        }
    }
}
