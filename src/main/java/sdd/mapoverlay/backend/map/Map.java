package sdd.mapoverlay.backend.map;

import sdd.mapoverlay.backend.points.EndPoint;
import sdd.mapoverlay.backend.points.types.PointType;
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

    public Map(String fileName){
        fileInitialization(fileName);
    }
    public void fileInitialization(String fileName){
        
        MapLoader mapLoader = new MapLoader(fileName);
        ArrayList<String> mapData = mapLoader.getMap();
        EndPoint upperEndPoint = null;
        EndPoint lowerEndPoint = null;

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
                    lowerEndPoint = new EndPoint(coordinates[0], coordinates[1], PointType.LOWERENDPOINT, PointType.LEFT);
                    upperEndPoint = new EndPoint(coordinates[2], coordinates[3], PointType.UPPERENDPOINT, PointType.RIGHT);
                } else { // x1 > x2
                    lowerEndPoint = new EndPoint(coordinates[0], coordinates[1], PointType.LOWERENDPOINT, PointType.RIGHT);
                    upperEndPoint = new EndPoint(coordinates[2], coordinates[3], PointType.UPPERENDPOINT, PointType.LEFT);
                }

            } else { // y1 >= y2
                if (coordinates[1].equals(coordinates[3])) { // y1 == y2
                    if (coordinates[0] < coordinates[2]) { // si x1 < x2
                        upperEndPoint = new EndPoint(coordinates[0], coordinates[1], PointType.UPPERENDPOINT, PointType.LEFT);
                        lowerEndPoint = new EndPoint(coordinates[2], coordinates[3], PointType.LOWERENDPOINT, PointType.RIGHT);
                    } else { // si x1 > x2
                        lowerEndPoint = new EndPoint(coordinates[0], coordinates[1], PointType.LOWERENDPOINT, PointType.RIGHT);
                        upperEndPoint = new EndPoint(coordinates[2], coordinates[3], PointType.UPPERENDPOINT, PointType.LEFT);
                    }
                } else { // si y1 > y2
                    if (coordinates[0] < coordinates[2]) { // si x1 < x2
                        upperEndPoint = new EndPoint(coordinates[0], coordinates[1], PointType.UPPERENDPOINT, PointType.LEFT);
                        lowerEndPoint = new EndPoint(coordinates[2], coordinates[3], PointType.LOWERENDPOINT, PointType.RIGHT);
                    } else { // si x1 > x2
                        upperEndPoint = new EndPoint(coordinates[0], coordinates[1], PointType.UPPERENDPOINT, PointType.RIGHT);
                        lowerEndPoint = new EndPoint(coordinates[2], coordinates[3], PointType.LOWERENDPOINT, PointType.LEFT);
                    }
                }
            }
            segments.add(new Segment(upperEndPoint, lowerEndPoint));

        }

    }

    public ArrayList<Segment> getSegments(){
        return segments;
    }

    public ArrayList<Segment> getVerticallySortedSegments(){
        Comparator<? super Segment> SegmentComparator = new SegmentComparator();
        segments.sort(SegmentComparator);
        Collections.reverse(segments);
        return segments;
    }

    public double getMaxYPosition(){
        return maxYPosition;
    }

    public double getMaxXPosition(){
        return maxXPosition;
    }

    public double getMinXPosition(){
        return minXPosition;
    }

    public double getMinYPosition(){
        return minYPosition;
    }

    private void updateMaxXPosition(double x){
        if (x > maxXPosition) {
            this.maxXPosition = x;
        }
    }

    private void updateMaxYPosition(double y){
        if (y > maxYPosition){
            this.maxYPosition = y;
        }
    }

    private void updateMinXPosition(double x){
        if (x < minXPosition){
            this.minXPosition = x;
        }
    }

    private void updateMinYPosition(double y){
        if (y < minYPosition){
            this.minYPosition = y;
        }
    }
}

