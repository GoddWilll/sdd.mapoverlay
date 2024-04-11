package sdd.mapoverlay.frontend.Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import sdd.mapoverlay.frontend.Widgets.CenterStack;
import sdd.mapoverlay.frontend.Widgets.SideMenu;
import sdd.mapoverlay.frontend.Widgets.TitlePane;

public class MainMenuScene {

    private BorderPane root;

    public MainMenuScene() {
        root = new BorderPane();
        SideMenu sideMenu = new SideMenu();

        // right side menu
        // root.setRight(sideMenu);

        // Top Pane
        TitlePane titlePane = new TitlePane(sideMenu);
        root.setTop(titlePane);

        // Center Pane
        CenterStack centerStack = new CenterStack(sideMenu);
        root.setCenter(centerStack);
    }

    public BorderPane getRoot() {
        return root;
    }
}
