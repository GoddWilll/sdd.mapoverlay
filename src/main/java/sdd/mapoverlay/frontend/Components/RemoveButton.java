package sdd.mapoverlay.frontend.Components;

import javafx.scene.control.Button;

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
                + "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
                + "-fx-font-family: 'Arial';";

        onHoverRemovebtnStyle = "-fx-cursor: hand;"
                + "-fx-text-fill: #637081;"
                + "-fx-background-color: transparent;"
                + "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
                + "-fx-font-family: 'Arial';";

        setStyle(removebtnStyle);
        setOnMouseEntered(e -> setOnHover());
        setOnMouseExited(e -> setOnExit());
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
