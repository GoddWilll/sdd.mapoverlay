package sdd.mapoverlay.frontend.Components;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import sdd.mapoverlay.backend.segments.Intersection;

public class CustomDrawingPane extends Pane {
    private Scale scaleTransform;
    CustomLine sweepline;
    private ArrayList<CustomPoint> intersectionPoints = new ArrayList<CustomPoint>();
    TranslateTransition sweeplineTransition;

    public CustomDrawingPane() {
        setScaleY(-1);

        setStyle("-fx-background-color: #F4EAD5;");
        scaleTransform = new Scale(1, 1);
        getTransforms().add(scaleTransform);

    }

    public void clear() {
        getChildren().clear();
    }

    public void setSize(double width, double height) {
        setMaxSize(width, height);
        getParent().setScaleX(3);
        getParent().setScaleY(3);
        StackPane.setAlignment(getParent(), Pos.CENTER);
        StackPane.setAlignment(this, Pos.CENTER);
        // setMaxHeight(200);
        // zoom(2, 2);

    }

    public void addDrawing(double[] points) {

        CustomLine line = new CustomLine(points[0], points[1], points[2], points[3]);
        getChildren().add(line.getLine());
        getChildren().add(line.getStartPoint());
        getChildren().add(line.getEndPoint());
    }

    public void addIntersection(ArrayList<Intersection> intersections) {
        intersections.sort((o1, o2) -> Double.compare(o1.getP().getY(), o2.getP().getY()));
        CustomPoint intersectionPoint;
        double x;
        double y;
        for (Intersection i : intersections) {
            // try {
            // wait(500);
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            x = i.getP().getX();
            y = i.getP().getY();
            intersectionPoint = new CustomPoint(x, y);
            intersectionPoint.setVisible(false);
            intersectionPoint.setId("intersection");

            // getChildren().add(intersectionPoint.getPoint());
            getChildren().add(intersectionPoint.getPoint());

            intersectionPoints.add(intersectionPoint);
        }

        // intersection.setVisible(false);

    }

    public void zoom(double factorx, double factory) {
        scaleTransform.setX(scaleTransform.getX() * factorx);
        scaleTransform.setY(scaleTransform.getY() * factory);
    }

    public void addSweepline() {
        if (sweepline != null) {
            getChildren().remove(sweepline.getLine());
        }

        sweepline = new CustomLine(0.0, 1.0, getWidth() + 15, 1.0);
        sweepline.setStrokeWidth(0.5);
        sweepline.setId("sweepline");
        sweepline.setStroke(javafx.scene.paint.Color.RED);
        getChildren().add(sweepline.getLine());
    }

    public void startSweepAnimation() {

        Duration duration = Duration.millis(5000);
        double time;
        sweepline.setTranslateY(intersectionPoints.get(0).getCenterY() - 10);

        for (CustomPoint point : intersectionPoints) {

            time = (duration.toMillis() / sweepline.getTranslateY()) * point.getCenterY();
            sweeplineTransition = new TranslateTransition(new Duration(time),
                    sweepline.getLine());
            // sweeplineTransition.setToY(point.getCenterY());
            sweeplineTransition.play();
            sweeplineTransition.setOnFinished(e -> {

                sweepline.setTranslateY(point.getCenterY() - 1);
                point.setVisible(true);
            });
        }
        // time = (duration.toMillis() / getHeight()) *
        // intersectionPoints.get(intersectionPoints.size() - 1).getCenterY();
        // sweeplineTransition = new TranslateTransition(new Duration(time),
        // sweepline.getLine());
        // sweeplineTransition.setToY(getHeight());
        // sweeplineTransition.play();

    }

}
