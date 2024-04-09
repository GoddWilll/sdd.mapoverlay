package sdd.mapoverlay.frontend.Widgets;

import javafx.geometry.Pos;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import sdd.mapoverlay.frontend.Components.CustomLineChart;

public class CenterStack extends StackPane {

    public static CustomLineChart lineChart = new CustomLineChart();

    public CenterStack(SideMenu sideMenu) {
        // Create LineChart
        getChildren().addAll(lineChart, sideMenu);

        // Set alignment
        StackPane.setAlignment(lineChart, Pos.CENTER);
        StackPane.setAlignment(sideMenu, Pos.CENTER_RIGHT);
    }

}
