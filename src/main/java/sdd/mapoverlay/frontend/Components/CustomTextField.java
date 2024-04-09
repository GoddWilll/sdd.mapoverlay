package sdd.mapoverlay.frontend.Components;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class CustomTextField extends TextField {

    private final String textFieldStyle;

    public CustomTextField() {
        textFieldStyle = "-fx-background-color: #F4EAD5; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 12px; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-padding: 8px ; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: transparent; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0); ";

        setStyle(textFieldStyle);
        setPrefWidth(40);
        setPrefHeight(30);

        // Adjust prompt text color to match text fill color
    }
}
