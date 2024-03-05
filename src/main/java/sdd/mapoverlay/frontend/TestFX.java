package sdd.mapoverlay.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.application.Application;
import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

public class TestFX extends Application {

    @Override
    public void start(Stage stage){
        Pane root = new Pane();
        root.setScaleY(-1); // Inverse l'axe des Y
        Map map = new Map("fichier1.txt");
        ArrayList<Segment> segments = map.getSegments();
        for (Segment segment : segments){
            Line line = new Line(segment.getLeftEndPoint().getXCoords(), segment.getLeftEndPoint().getYCoords(), segment.getRightEndPoint().getXCoords(), segment.getRightEndPoint().getYCoords());
            root.getChildren().addAll(line);
        }
        double maxX = map.getMaxXPosition();
        double maxY = map.getMaxYPosition();

        Scene scene = new Scene(root, maxX+10, maxY+10);

        stage.setScene(scene);
        stage.setTitle("Affichage de lignes en JavaFX");
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}