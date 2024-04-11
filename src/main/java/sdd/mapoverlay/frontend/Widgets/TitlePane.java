package sdd.mapoverlay.frontend.Widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sdd.mapoverlay.frontend.Constants.ConstantStyles;
import sdd.mapoverlay.frontend.Components.CustomButton;

public class TitlePane extends StackPane {

    public TitlePane(SideMenu sideMenu) {

        Label titleLabel = new Label("Map Overlay");
        String titleStyle = "-fx-font-size: 35; " +
                "-fx-font-family: 'Times New Roman'; " + // Change font family
                "-fx-text-fill: black; " +
                "-fx-padding: 8px 16px; " +
                "-fx-font-weight:900;";
        titleLabel.setStyle(titleStyle);

        CustomButton toggleButton = new CustomButton("");
        toggleButton.setStyle(ConstantStyles.buttonStyle);
        toggleButton.setText(
                sideMenu.isMenuVisible() ? "Hide Menu" : "Show Menu");
        // toggleButton.setStyle("-fx-background-color: #000; -fx-text-fill: white;");
        toggleButton.setOnAction(event -> {
            sideMenu.toggleMenu();
            toggleButton.setText(
                    sideMenu.isMenuVisible() ? "Hide Menu" : "Show Menu");
        });

        /**
         * ImageView arrowIcon = createArrowIcon();
         * toggleButton.setGraphic(arrowIcon);
         * toggleButton.setOnAction(event -> {
         * sideMenu.toggleMenu();
         * arrowIcon.setImage(
         * sideMenu.isMenuVisible() ? createArrowImage("/arrow-back.png")
         * : createArrowImage("/arrow-forward.png"));
         * });
         * 
         */

        HBox header = new HBox(titleLabel, toggleButton);
        header.setAlignment(Pos.TOP_RIGHT);
        header.setSpacing(50);

        StackPane.setAlignment(toggleButton, Pos.TOP_RIGHT);
        getChildren().addAll(titleLabel, toggleButton);
        setBackgroundStyle();
    }

    // private Image createArrowImage(String arrowPath) {
    // return new Image(getClass().getResourceAsStream(arrowPath));
    // }

    // private ImageView createArrowIcon() {
    // Image arrowImage = createArrowImage("/arrow-back.png");
    // return new ImageView(arrowImage);
    // }

    private void setBackgroundStyle() {
        StringBuilder styleBuilder = new StringBuilder();
        styleBuilder.append("-fx-background-color: ")
                .append(formatColor(ConstantStyles.SECONDARY_COLOR))
                .append("; -fx-pref-width: 100%; ")
                .append("-fx-padding: 10px; ")
                .append("-fx-min-height: 100px;")
                .append("-fx-border-color: #000;")

        ; // Fixed typo in fx-preferred-height

        setStyle(styleBuilder.toString());
    }

    private String formatColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

}
