package sdd.mapoverlay.backend.segments;

public class Vector {
    private double x;
    private double y;

    /**
     * Constructeur pour un vecteur
     * 
     * @param x double representant la coordonnee x
     * @param y double representant la coordonnee y
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Permet de recuperer la coordonnee x du vecteur
     * 
     * @return double representant la coordonnee x
     */
    public double getX() {
        return x;
    }

    /**
     * Permet de recuperer la coordonnee y du vecteur
     * 
     * @return double representant la coordonnee y
     */
    public double getY() {
        return y;
    }

    /**
     * Permet de recuperer une representation du vecteur sous forme de chaine de
     * caracteres
     * 
     * @return String representant le vecteur
     */
    @Override
    public String toString() {
        return "(" + this.x + "; " + this.y + ")";
    }
}
