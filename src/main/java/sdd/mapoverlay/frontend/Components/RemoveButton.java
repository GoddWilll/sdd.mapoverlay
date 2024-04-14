package sdd.mapoverlay.frontend.Components;

import javafx.scene.control.Button;

/**
 * A custom button used for removing items.
 */
public class RemoveButton extends Button {

    private final String removebtnStyle;
    private final String onHoverRemovebtnStyle;
    private int index;

    /**
     * Constructs a new RemoveButton with the specified index and text.
     *
     * @param index the index of the button
     * @param text  the text to be displayed on the button
     */
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

    /**
     * Sets the style of the button when the mouse hovers over it.
     */
    public void setOnHover() {
        setStyle(onHoverRemovebtnStyle);
    }

    /**
     * Sets the style of the button when the mouse exits it.
     */
    public void setOnExit() {
        setStyle(removebtnStyle);
    }

    /**
     * Returns the index of the button.
     *
     * @return the index of the button
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index of the button.
     *
     * @param index the index of the button
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
