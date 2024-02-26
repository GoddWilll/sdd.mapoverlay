package sdd.mapoverlay.frontend;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.application.Application;
import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

public class TestFX extends Application {

    /**
    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }**/

    @Override
    public void start(Stage stage){
        Pane root = new Pane();
        Map map = new Map("fichier4.txt");
        ArrayList<Segment> segments = map.getSegments();
        for (Segment segment : segments){
            Line line = new Line(segment.getLeftEndPoint().getX_coords(), segment.getLeftEndPoint().getY_coords(), segment.getRightEndPoint().getX_coords(), segment.getRightEndPoint().getY_coords());
            root.getChildren().addAll(line);
        }
        double maxX = map.getMaxXPosition();
        double maxY = map.getMaxYPosition();
        double minX = map.getMinXPosition();
        double minY = map.getMinYPosition();

        Scene scene = new Scene(root, maxX+10, maxY+10);

        stage.setScene(scene);
        stage.setTitle("Affichage de lignes en JavaFX");
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}