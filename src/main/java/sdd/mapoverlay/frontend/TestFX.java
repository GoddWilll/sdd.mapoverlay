package sdd.mapoverlay.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.application.Application;
import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.segments.Intersection;
import sdd.mapoverlay.backend.segments.Segment;
import sdd.mapoverlay.backend.Logic;
import java.util.ArrayList;

public class TestFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        root.setScaleY(-1); // Inverse l'axe des Y
        Scale scaleTransform = new Scale();
        scaleTransform.setX(3);
        scaleTransform.setY(3);

        Map map = new Map("test.txt");
        ArrayList<Intersection> intersections = Logic.findIntersection("test.txt");

        for (Segment segment : map.getSegments()){
            Line line = new Line(segment.getLeftEndPoint().getXCoords(), segment.getLeftEndPoint().getYCoords(), segment.getRightEndPoint().getXCoords(), segment.getRightEndPoint().getYCoords());
            root.getChildren().addAll(line);
        }

        for (Intersection intersection : intersections){
            Circle circle = new Circle();
            circle.setCenterX(intersection.getP().getXCoords());
            circle.setCenterY(intersection.getP().getYCoords());
            circle.setRadius(2);
            circle.setStroke(Color.RED);
            circle.setFill(Color.RED);
            root.getChildren().addAll(circle);
        }


        root.getTransforms().addAll(scaleTransform);
        Scene scene = new Scene(root, 800, 800);

        stage.setScene(scene);
        stage.setTitle("Affichage de lignes en JavaFX");
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}