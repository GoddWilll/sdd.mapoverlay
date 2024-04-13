package sdd.mapoverlay.frontend.Components;

import javafx.scene.shape.Circle;
import sdd.mapoverlay.frontend.Constants.ConstantStyles;

public class CustomLine extends javafx.scene.shape.Line {
    private Circle startPoint;
    private Circle endPoint;

    public CustomLine(Double x1, Double y1, Double x2, Double y2) {
        super(x1, y1, x2, y2);
        startPoint = new Circle(x1, y1, 1); // (centerX, centerY, radius)
        endPoint = new Circle(x2, y2, 1);
        startPoint.setFill(ConstantStyles.SECONDARY_COLOR);
        endPoint.setFill(ConstantStyles.SECONDARY_COLOR);
        setStrokeWidth(0.5);
        setStroke(javafx.scene.paint.Color.BLACK);

    }

    public CustomLine getLine() {
        return this;
    }

    public Circle getStartPoint() {
        return startPoint;
    }

    public Circle getEndPoint() {
        return endPoint;
    }

}
