package sdd.mapoverlay.frontend;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import sdd.mapoverlay.frontend.Scenes.MainMenuScene;

import java.io.*;

public class TestFX extends Application {

    @Override

    public void start(Stage primaryStage) {
        MainMenuScene example = new MainMenuScene();
        Scene scene = new Scene(example.getRoot(), 1280, 1024);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    public InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

}