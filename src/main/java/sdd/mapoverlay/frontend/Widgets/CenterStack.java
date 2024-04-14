package sdd.mapoverlay.frontend.Widgets;

import javafx.geometry.Pos;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import sdd.mapoverlay.frontend.Components.CustomDrawingPane;

/**
 * The CenterStack class represents a custom BorderPane that serves as the
 * central container for the application's UI.
 * It contains a drawing pane, side menu, and banners.
 */
public class CenterStack extends BorderPane {

    /**
     * The drawing pane used for custom drawings.
     */
    public static CustomDrawingPane drawingPane = new CustomDrawingPane();

    /**
     * Constructs a new CenterStack object with the specified side menu.
     *
     * @param sideMenu the side menu to be displayed on the right side of the center
     *                 stack.
     */
    public CenterStack(SideMenu sideMenu) {
        Pane bottomBanner = new Pane();
        bottomBanner.setPrefHeight(50);
        bottomBanner.setStyle("-fx-background-color: #AFD5AA; ");

        Pane topBanner = new Pane();
        topBanner.setPrefHeight(50);
        topBanner.setStyle("-fx-background-color: #AFD5AA; ");

        Pane leftBanner = new Pane();
        leftBanner.setPrefWidth(50);
        leftBanner.setStyle("-fx-background-color: #AFD5AA; ");

        StackPane centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.getChildren().add(drawingPane);

        // Create LineChart
        // getChildren().addAll(drawingPane, sideMenu);

        // Add LineChart to the center
        setCenter(centerPane);

        // Add SideMenu to the right
        setRight(sideMenu);

        // Add top banner
        setTop(topBanner);

        // Add right banner
        setLeft(leftBanner);

        // Add bottom banner
        setBottom(bottomBanner);
        // Set style
        setStyle("-fx-background-color: #F4EAD5; ");
    }

}
