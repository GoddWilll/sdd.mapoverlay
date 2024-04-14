package sdd.mapoverlay.backend.segments;

/**
 * Represents a vector in a 2D space.
 */
public class Vector {
    private double x;
    private double y;

    /**
     * Constructor for a vector.
     * 
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the vector.
     * 
     * @return The x-coordinate of the vector.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the vector.
     * 
     * @return The y-coordinate of the vector.
     */
    public double getY() {
        return y;
    }

    /**
     * Returns a string representation of the vector.
     * 
     * @return A string representation of the vector.
     */
    @Override
    public String toString() {
        return "(" + this.x + "; " + this.y + ")";
    }
}
