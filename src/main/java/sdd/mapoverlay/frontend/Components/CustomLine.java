package sdd.mapoverlay.frontend.Components;

import javafx.scene.shape.Circle;
import sdd.mapoverlay.frontend.Constants.ConstantStyles;

/**
 * Represents a custom line in a JavaFX application.
 */
public class CustomLine extends javafx.scene.shape.Line {
    private Circle startPoint;
    private Circle endPoint;

    /**
     * Constructs a CustomLine object with the specified coordinates.
     *
     * @param x1 the x-coordinate of the starting point
     * @param y1 the y-coordinate of the starting point
     * @param x2 the x-coordinate of the ending point
     * @param y2 the y-coordinate of the ending point
     */
    public CustomLine(Double x1, Double y1, Double x2, Double y2) {
        super(x1, y1, x2, y2);
        startPoint = new Circle(x1, y1, 1); // (centerX, centerY, radius)
        endPoint = new Circle(x2, y2, 1);
        startPoint.setFill(ConstantStyles.SECONDARY_COLOR);
        endPoint.setFill(ConstantStyles.SECONDARY_COLOR);
        setStrokeWidth(0.5);
        setStroke(javafx.scene.paint.Color.BLACK);
    }

    /**
     * Returns the CustomLine object.
     *
     * @return the CustomLine object
     */
    public CustomLine getLine() {
        return this;
    }

    /**
     * Returns the starting point of the line.
     *
     * @return the starting point of the line
     */
    public Circle getStartPoint() {
        return startPoint;
    }

    /**
     * Returns the ending point of the line.
     *
     * @return the ending point of the line
     */
    public Circle getEndPoint() {
        return endPoint;
    }
}
