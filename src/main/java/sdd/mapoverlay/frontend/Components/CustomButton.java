package sdd.mapoverlay.frontend.Components;

import javafx.scene.control.Button;

public class CustomButton extends Button {

    private final String buttonStyle;
    private final String onHoverButtonStyle;

    public CustomButton(String text) {
        super(text);
        buttonStyle = "-fx-background-color: #F4EAD5; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-padding: 8px 16px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: transparent; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0); ";

        onHoverButtonStyle = "-fx-background-color: #F4EAD5; " +
                "-fx-text-fill: #DC851F; " +
                "-fx-font-size: 16px; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-padding: 8px 16px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: white; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0); ";

        setStyle(buttonStyle);

        // Set up event handlers for hover effect
        setOnMouseEntered(e -> setStyle(onHoverButtonStyle));
        setOnMouseExited(e -> setStyle(buttonStyle));
        // setPrefHeight(40);
        // setMinHeight(40);
        // setMinWidth(40);
    }
}
