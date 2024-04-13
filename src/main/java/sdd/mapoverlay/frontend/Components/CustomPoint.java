package sdd.mapoverlay.frontend.Components;

import java.io.ObjectInputStream.GetField;

public class CustomPoint extends javafx.scene.shape.Circle {

    public CustomPoint(Double x, Double y) {
        super(x, y, 1); // (centerX, centerY, radius)
        setFill(sdd.mapoverlay.frontend.Constants.ConstantStyles.ACCENT_COLOR);

    }

    public CustomPoint getPoint() {
        return this;
    }

}
