package sdd.mapoverlay.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Pos;
import sdd.mapoverlay.frontend.Widgets.CenterStack;
import sdd.mapoverlay.frontend.Widgets.SideMenu;

import java.io.*;

/**
 * This class represents a JavaFX application for testing purposes.
 */
public class TestFX extends Application {

    /**
     * The entry point for the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        SideMenu sideMenu = new SideMenu();

        // right side menu
        // root.setRight(sideMenu);

        // Top Pane

        // Center Pane
        CenterStack centerStack = new CenterStack(sideMenu);
        root.getChildren().add(centerStack);
        StackPane.setAlignment(centerStack, Pos.CENTER);
        Scene scene = new Scene(root, 1280, 1024);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Example");
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

}