package sdd.mapoverlay.frontend.Components;

/*
 * CustomPoint class
 */
public class CustomPoint extends javafx.scene.shape.Circle {

    /**
     * Constructs a CustomPoint object with the specified coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public CustomPoint(Double x, Double y) {
        super(x, y, 1); // (centerX, centerY, radius)
        setFill(sdd.mapoverlay.frontend.Constants.ConstantStyles.ACCENT_COLOR);

    }

    /**
     * Returns the CustomPoint object.
     *
     * @return the CustomPoint object
     */
    public CustomPoint getPoint() {
        return this;
    }

}
