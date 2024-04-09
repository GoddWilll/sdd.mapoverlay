package sdd.mapoverlay.frontend.Components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RemoveButton extends Button {

    private final String removebtnStyle;
    private final String onHoverRemovebtnStyle;
    private int index;

    public RemoveButton(int index, String text) {
        this.index = index;
        setText(text);
        removebtnStyle = "-fx-cursor: hand;"
                + "-fx-text-fill: red;"
                + "-fx-background-color: transparent;"
                + "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
                + "-fx-font-family: 'Arial';";

        onHoverRemovebtnStyle = "-fx-cursor: hand;"
                + "-fx-text-fill: red;"
                + "-fx-background-color: transparent;"
                + "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
                + "-fx-cursor: hand; "
                + "-fx-font-family: 'Arial';";

        setStyle(removebtnStyle);

    }

    public void setOnHover() {
        setStyle(onHoverRemovebtnStyle);
    }

    public void setOnExit() {
        setStyle(removebtnStyle);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
