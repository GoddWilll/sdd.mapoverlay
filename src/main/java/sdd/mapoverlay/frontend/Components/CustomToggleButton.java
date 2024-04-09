package sdd.mapoverlay.frontend.Components;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class CustomToggleButton extends RadioButton {

    private final String togglebtnStyle;
    private final String onHoverTogglebtnStyle;

    public CustomToggleButton(String text) {
        super(text);
        togglebtnStyle = "-fx-background-color: #F4EAD5; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-padding: 8px 16px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: transparent; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0); ";

        onHoverTogglebtnStyle = "-fx-background-color: #F4EAD5; " +
                "-fx-text-fill: #DC851F; " +
                "-fx-font-size: 16px; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-padding: 8px 16px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: white; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0); ";

        setStyle(togglebtnStyle);

        if (isSelected()) {
            setStyle(text + "-fx-background-color: #DC851F; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-family: 'Arial'; " +
                    "-fx-padding: 8px 16px; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-border-radius: 8px; " +
                    "-fx-border-color: white; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0); ");
        } else {
            setStyle(togglebtnStyle);
        }
        setPrefHeight(40);
        setMinHeight(40);
    }

}