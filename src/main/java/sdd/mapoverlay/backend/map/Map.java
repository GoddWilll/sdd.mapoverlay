package sdd.mapoverlay.backend.map;

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.points.types.Position;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.utils.MapLoader;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 * Represents a map containing segments and points.
 */
public class Map {
    private ArrayList<Segment> segments = new ArrayList<>();

    /**
     * Constructs a Map object with the specified file name.
     *
     * @param fileName the name of the file to load the map from
     */
    public Map(String fileName) {
        fileInitialization(fileName);
    }

    /**
     * Initializes the map by loading data from the specified file.
     *
     * @param fileName the name of the file to load the map from
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

                Segment upperPointSegment = new Segment(upperEndPoint, lowerEndPoint);
                upperEndPoint.addSegment(upperPointSegment);

                segments.add(new Segment(upperEndPoint, lowerEndPoint));

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading map file please select a valid file");
        }

    }

    /**
     * Returns the list of segments in the map.
     *
     * @return the list of segments
     */
    public ArrayList<Segment> getSegments() {
        return segments;
    }

}
