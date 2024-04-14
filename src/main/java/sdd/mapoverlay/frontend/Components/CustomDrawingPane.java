
package sdd.mapoverlay.frontend.Components;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import sdd.mapoverlay.backend.segments.Intersection;

/**
 * A custom drawing pane that allows drawing lines and displaying intersections.
 * 
 */
public class CustomDrawingPane extends Pane {
    private Scale scaleTransform;
    private CustomLine sweepline;
    private ArrayList<CustomPoint> intersectionPoints = new ArrayList<CustomPoint>();
    private TranslateTransition sweeplineTransition;

    /**
     * Constructs a new CustomDrawingPane.
     */
    public CustomDrawingPane() {
        setScaleY(-1);

        setStyle("-fx-background-color: #F4EAD5;");
        scaleTransform = new Scale(1, 1);
        getTransforms().add(scaleTransform);
    }

    /**
     * Clears all drawings from the pane.
     */
    public void clear() {
        getChildren().clear();
    }

    /**
     * Sets the size of the pane.
     * 
     * @param width  the width of the pane
     * @param height the height of the pane
     */
    public void setSize(double width, double height) {
        setMaxSize(width, height);
        getParent().setScaleX(3);
        getParent().setScaleY(3);
        StackPane.setAlignment(getParent(), Pos.CENTER);
        StackPane.setAlignment(this, Pos.CENTER);
    }

    /**
     * Adds a drawing to the pane.
     * 
     * @param points an array of four points representing the start and end
     *               coordinates of the line
     */
    public void addDrawing(double[] points) {
        CustomLine line = new CustomLine(points[0], points[1], points[2], points[3]);
        getChildren().add(line.getLine());
        getChildren().add(line.getStartPoint());
        getChildren().add(line.getEndPoint());
    }

    /**
     * Adds intersections to the pane.
     * 
     * @param intersections an array list of Intersection objects representing the
     *                      intersections
     * @param hide          a flag indicating whether to hide the intersections
     */
    public void addIntersection(ArrayList<Intersection> intersections, boolean hide) {
        intersections.sort((o1, o2) -> Double.compare(o1.getP().getY(), o2.getP().getY()));
        CustomPoint intersectionPoint;
        double x;
        double y;
        for (Intersection i : intersections) {
            x = i.getP().getX();
            y = i.getP().getY();
            intersectionPoint = new CustomPoint(x, y);
            if (hide) {
                intersectionPoint.setVisible(false);
            }
            intersectionPoint.setId("intersection");
            getChildren().add(intersectionPoint.getPoint());
            intersectionPoints.add(intersectionPoint);
        }
    }

    /**
     * Zooms the pane by the specified factors.
     * 
     * @param factorx the zoom factor for the x-axis
     * @param factory the zoom factor for the y-axis
     */
    public void zoom(double factorx, double factory) {
        scaleTransform.setX(scaleTransform.getX() * factorx);
        scaleTransform.setY(scaleTransform.getY() * factory);
    }

    /**
     * Adds a sweepline to the pane.
     */
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

    /**
     * Starts the sweep animation.
     */
    public void startSweepAnimation() {
        Duration duration = Duration.millis(900);
        double time;
        sweepline.setTranslateY(intersectionPoints.get(0).getCenterY() - 10);
        for (CustomPoint point : intersectionPoints) {
            time = (duration.toMillis() / sweepline.getTranslateY()) * point.getCenterY();
            sweeplineTransition = new TranslateTransition(new Duration(time), sweepline.getLine());
            sweeplineTransition.play();
            sweeplineTransition.setOnFinished(e -> {
                sweepline.setTranslateY(point.getCenterY() - 1);
                point.setVisible(true);
            });
        }
    }
}
