package sdd.mapoverlay.frontend.Components;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;

/**
 * A custom implementation of the JavaFX TextField with customized styling.
 */
public class CustomTextField extends TextField {

    private final String textFieldStyle;

    /**
     * Constructs a new CustomTextField with the default styling.
     */
    public CustomTextField() {
        textFieldStyle = "-fx-background-color: #F4EAD5; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 12px; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: transparent; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0); ";

        setStyle(textFieldStyle);
        setPrefWidth(90);
        setPrefHeight(20);

    }
}
