package sdd.mapoverlay.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;

public class Menu extends Scene {

    public Menu(@SuppressWarnings("exports") StackPane root, double width, double height) {
        super(root, width, height);
        root.setStyle("-fx-background-color: #FFFFFF;");
        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(20);
        menu.setPadding(new Insets(20, 20, 20, 20));
        Label title = new Label("Map Overlay");
        title.setFont(new Font("Arial", 40));
        title.setTextFill(Color.web("#000000"));
        menu.getChildren().add(title);
        Label mapLabel = new Label("Choose a map");
        mapLabel.setFont(new Font("Arial", 20));
        mapLabel.setTextFill(Color.web("#000000"));
        menu.getChildren().add(mapLabel);
        ComboBox<String> mapList = new ComboBox<String>();
        File folder = new File("src/main/resources/maps");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                mapList.getItems().add(file.getName());
            }
        }
        menu.getChildren().add(mapList);
        Label speedLabel = new Label("Choose the speed");
        speedLabel.setFont(new Font("Arial", 20));
        speedLabel.setTextFill(Color.web("#000000"));
        menu.getChildren().add(speedLabel);
        Slider speedSlider = new Slider(1, 10, 5);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(1);
        speedSlider.setMinorTickCount(0);
        speedSlider.setBlockIncrement(1);
        menu.getChildren().add(speedSlider);
        root.getChildren().add(menu);
    }

}
