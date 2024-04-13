package sdd.mapoverlay.frontend.Components;

import javafx.scene.control.Label;

public class CustomLabel extends Label {

    private final String labelStyle;

    public CustomLabel(String text) {
        super(text);
        labelStyle = "-fx-font-size: 20; " +
                "-fx-font-family: 'Arial'; " + // Change font family
                "-fx-text-fill: black; " +
                "-fx-font-weight:bold";
        setStyle(labelStyle);
    }

    public void setTitleStyle() {
        setStyle("-fx-font-size: 30; " +
                "-fx-font-family: 'Arial'; " + // Change font family
                "-fx-text-fill: black; " +
                "-fx-font-weight:bold;");
    }

    public void setSegmentStyle() {
        setStyle("-fx-font-size: 15; " +
                "-fx-font-family: 'Arial'; " + // Change font family
                "-fx-text-fill: black; " +
                "-fx-font-weight:bold;");
    }
}