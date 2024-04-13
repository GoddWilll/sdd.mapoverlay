package sdd.mapoverlay.frontend.Widgets;

import javafx.geometry.Pos;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import sdd.mapoverlay.frontend.Components.CustomDrawingPane;

public class CenterStack extends BorderPane {

    public static CustomDrawingPane drawingPane = new CustomDrawingPane();

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
