package sdd.mapoverlay.frontend.Widgets;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sdd.mapoverlay.backend.Logic;
import sdd.mapoverlay.backend.segments.Intersection;
import sdd.mapoverlay.backend.utils.MapLoader;
import sdd.mapoverlay.frontend.Components.CustomButton;
import sdd.mapoverlay.frontend.Components.CustomLabel;
import sdd.mapoverlay.frontend.Components.CustomTextField;
import sdd.mapoverlay.frontend.Components.CustomToggleButton;
import sdd.mapoverlay.frontend.Components.RemoveButton;
import sdd.mapoverlay.frontend.Constants.ConstantStyles;
import javafx.util.Duration;
import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.segments.Segment;;

public class SideMenu extends VBox {

    private TranslateTransition showTransition;
    private TranslateTransition hideTransition;
    private boolean isMenuVisible = true;
    private ArrayList<Segment> segmentsToDraw = new ArrayList<>();
    private ArrayList<Segment> selectedSegments = new ArrayList<>();
    private File openedFile;

    public SideMenu() {

        // Enter segments section
        VBox enterSegmentsBox = new VBox();

        CustomLabel enterSegmentsLabel = new CustomLabel("Enter segments");
        enterSegmentsLabel.setTitleStyle();
        VBox enterSegmentsInputBox = new VBox();
        enterSegmentsBox.setSpacing(10);
        HBox enterSegmentsInputRow1 = new HBox();
        HBox enterSegmentsInputRow2 = new HBox();
        enterSegmentsInputRow1.setSpacing(10);
        enterSegmentsInputRow1.setAlignment(Pos.CENTER);
        HBox.setHgrow(enterSegmentsInputRow1, Priority.ALWAYS);
        enterSegmentsInputRow1.setMaxWidth(Double.MAX_VALUE * 0.7);
        enterSegmentsInputRow1.paddingProperty().set(new Insets(10, 10, 10, 10));
        enterSegmentsInputRow2.setSpacing(10);
        enterSegmentsInputRow2.setAlignment(Pos.CENTER);
        HBox.setHgrow(enterSegmentsInputRow2, Priority.ALWAYS);
        enterSegmentsInputRow2.setMaxWidth(Double.MAX_VALUE * 0.7);
        enterSegmentsInputRow2.paddingProperty().set(new Insets(10, 10, 10, 10));

        CustomTextField xCoordStart = new CustomTextField();
        xCoordStart.setPromptText("x1");
        CustomTextField yCoordStart = new CustomTextField();
        enterSegmentsInputRow1.getChildren().addAll(new CustomLabel("("), xCoordStart, new CustomLabel(";"),
                yCoordStart, new CustomLabel(")"));

        CustomTextField xCoordEnd = new CustomTextField();
        CustomTextField yCoordEnd = new CustomTextField();
        enterSegmentsInputRow2.getChildren().addAll(new CustomLabel("("), xCoordEnd, new CustomLabel(";"), yCoordEnd,
                new CustomLabel(")"));

        CustomButton addButton = new CustomButton("+");
        HBox.setHgrow(addButton, Priority.ALWAYS);
        addButton.setMaxWidth(Double.MAX_VALUE);
        enterSegmentsInputBox.getChildren().addAll(enterSegmentsInputRow1, enterSegmentsInputRow2);

        HBox enterSegmentsInputBox2 = new HBox();
        enterSegmentsInputBox2.getChildren().addAll(enterSegmentsInputBox, addButton);
        enterSegmentsInputBox2.setSpacing(10);
        enterSegmentsInputBox2.setAlignment(Pos.CENTER);

        enterSegmentsBox.setSpacing(5);
        enterSegmentsBox.setAlignment(Pos.TOP_LEFT);
        enterSegmentsInputBox.setSpacing(10);
        enterSegmentsInputBox.setAlignment(Pos.CENTER);
        enterSegmentsBox.getChildren().addAll(enterSegmentsLabel, enterSegmentsInputBox2);

        // Action button row

        HBox actionButtonRow = new HBox();
        actionButtonRow.setSpacing(10);
        CustomButton clearButton = new CustomButton("Clear");
        CustomButton plotButton = new CustomButton("Plot");
        HBox.setHgrow(clearButton, Priority.ALWAYS);
        HBox.setHgrow(plotButton, Priority.ALWAYS);
        clearButton.setMaxWidth(Double.MAX_VALUE);
        plotButton.setMaxWidth(Double.MAX_VALUE);

        actionButtonRow.getChildren().addAll(clearButton, plotButton);

        // Show Sweep Line
        CustomToggleButton showSweepLineButton = new CustomToggleButton("Show Sweep Line");
        CustomToggleButton hideSweepLineButton = new CustomToggleButton("Hide Sweep Line");
        HBox.setHgrow(showSweepLineButton, Priority.ALWAYS);
        HBox.setHgrow(hideSweepLineButton, Priority.ALWAYS);
        showSweepLineButton.setMaxWidth(Double.MAX_VALUE);
        hideSweepLineButton.setMaxWidth(Double.MAX_VALUE);
        ToggleGroup sweepLineGroup = new ToggleGroup();
        showSweepLineButton.setToggleGroup(sweepLineGroup);
        hideSweepLineButton.setToggleGroup(sweepLineGroup);
        showSweepLineButton.setSelected(true);
        HBox sweepLineButtonBox = new HBox();
        sweepLineButtonBox.setSpacing(10);
        sweepLineButtonBox.getChildren().addAll(showSweepLineButton, hideSweepLineButton);

        // Action buttons
        CustomButton browseButton = new CustomButton("Load from file");
        CustomButton saveButton = new CustomButton("Save in new file");
        CustomButton editButton = new CustomButton("Edit current file");
        // Set HBox.hgrow property to Priority.ALWAYS for each button
        HBox.setHgrow(browseButton, Priority.ALWAYS);
        HBox.setHgrow(saveButton, Priority.ALWAYS);
        HBox.setHgrow(editButton, Priority.ALWAYS);
        browseButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setMaxWidth(Double.MAX_VALUE);
        editButton.setMaxWidth(Double.MAX_VALUE);

        VBox actionButtonColumn = new VBox();
        actionButtonColumn.setSpacing(10);
        actionButtonColumn.getChildren().addAll(browseButton, saveButton, editButton);

        // Add components to the SideMenu

        // Vbox inside ScrollPane
        VBox textSegmentsBox = new VBox();
        textSegmentsBox.setSpacing(15);
        textSegmentsBox.setPadding(new Insets(10, 0, 20, 10));
        VBox.setMargin(textSegmentsBox, new Insets(10, 0, 20, 0));

        saveButton.setOnAction(event -> {
            SaveFilePopup.showSaveFileDialog(selectedSegments);
        });

        browseButton.setOnAction(event ->

        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            openedFile = fileChooser.showOpenDialog(null);
            if (openedFile != null) {
                Map map = new Map(openedFile.getAbsolutePath());
                try {
                    List<Segment> segments = map.getSegments();

                    for (Segment segment : segments) {
                        String segmentText = formatSegmentToString(segment.toString());
                        addToVbox(textSegmentsBox, segment.getId(), segmentText);
                        selectedSegments.add(segment);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        editButton.setOnAction(event -> {
            if (openedFile != null) {
                writeSegmentsToFile(openedFile);
            } else {
                // Inform the user to open a file first
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please open a file first.");
                alert.showAndWait();
            }
        });
        clearButton.setOnAction(event -> {
            selectedSegments.clear();
            segmentsToDraw.clear();
            CenterStack.lineChart.clearLineChart();
            textSegmentsBox.getChildren().clear();
        });
        addButton.setOnAction(event -> {
            double x1 = Double.parseDouble(xCoordStart.getText());

            double y1 = Double.parseDouble(yCoordStart.getText());
            double x2 = Double.parseDouble(xCoordEnd.getText());
            double y2 = Double.parseDouble(yCoordEnd.getText());
            xCoordStart.clear();
            yCoordStart.clear();
            xCoordEnd.clear();
            yCoordEnd.clear();
            try {
                Segment newSegment = new Segment(x1, y1, x2, y2);
                String segmentText = formatSegmentToString(new String(x1 + " " + y1 + " " + x2 + " " + y2));

                selectedSegments.add(newSegment);
                addToVbox(textSegmentsBox, newSegment.getId(), segmentText);

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        VBox scrollVBox = new VBox();

        CustomLabel segmentsLabel = new CustomLabel("Segments to be scanned");
        segmentsLabel.setTitleStyle();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(200.0);
        scrollPane.setPrefWidth(200.0);
        scrollPane.setContent(textSegmentsBox);

        CustomButton startScanButton = new CustomButton("Start scan");
        HBox.setHgrow(startScanButton, Priority.ALWAYS);
        startScanButton.setMaxWidth(Double.MAX_VALUE);
        startScanButton.setOnAction(
                event -> {
                    CenterStack.lineChart.activateSweepline();
                    this.toggleMenu();
                    // if (selectedSegments.size() > 0) {
                    ArrayList<Intersection> intersections = Logic.findIntersection(segmentsToDraw);
                    System.out.println("INTERSECTIONS : " + intersections);
                    // } else {
                    // // Inform the user to add segments first
                    // Alert alert = new Alert(Alert.AlertType.ERROR);
                    // alert.setTitle("Error");
                    // alert.setHeaderText(null);
                    // alert.setContentText("Please add segments first.");
                    // alert.showAndWait();
                    // }
                });

        plotButton.setOnAction(event -> {
            if (selectedSegments.size() > 0) {
                segmentsToDraw.clear();
                segmentsToDraw.addAll(selectedSegments);
                CenterStack.lineChart.clearLineChart();
                for (Segment segment : segmentsToDraw) {
                    CenterStack.lineChart.addSeries(segment.getSerie());
                }
                // this.toggleMenu();
            } else {
                // Inform the user to add segments first
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please add segments first.");
                alert.showAndWait();
            }
        });

        scrollVBox.getChildren().addAll(segmentsLabel, scrollPane, actionButtonRow);
        scrollVBox.setSpacing(10);

        getChildren().addAll(enterSegmentsBox, scrollVBox, actionButtonColumn, sweepLineButtonBox, startScanButton);

        this.setSpacing(50);
        setPadding(new Insets(20, 20, 20, 20));
        alignmentProperty().set(javafx.geometry.Pos.TOP_CENTER);

        setBackgroundStyle();
        initializeTransitions();

    }

    private void writeSegmentsToFile(File file) {
        try {
            FileWriter writer = new FileWriter(file);

            // Writing each segment to the file
            for (Segment segment : selectedSegments) {
                // Format the segment string to desired format
                String formattedSegment = segment.toString();
                writer.write(formattedSegment + "\n");
            }

            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Segments saved to file successfully");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error writing to file: " + e.getMessage());
        }
    }

    private void setBackgroundStyle() {
        StringBuilder styleBuilder = new StringBuilder();
        styleBuilder.append("-fx-background-color: ")
                .append(formatColor(ConstantStyles.SECONDARY_COLOR))
                .append("; -fx-justify: center;")
                .append("-fx-min-width: 400px;")
                .append("-fx-max-width: 400px;")
                .append("-fx-min-height: 924px;")
                .append("fx-padding: 10px; ")

        ; // Adjusted width to 60px

        setStyle(styleBuilder.toString());
    }

    private String formatColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void initializeTransitions() {
        // Define translation transitions
        showTransition = new TranslateTransition(Duration.millis(300), this);
        showTransition.setFromX(400); // Move from the left
        showTransition.setToX(-1); // Move to original position

        hideTransition = new TranslateTransition(Duration.millis(300), this);
        hideTransition.setFromX(0); // Move from original position
        hideTransition.setToX(400); // Move to the left
    }

    public void toggleMenu() {
        if (isMenuVisible) {
            hideTransition.play();
        } else {
            showTransition.play();
        }
        isMenuVisible = !isMenuVisible;
    }

    public boolean isMenuVisible() {
        return isMenuVisible;
    }

    private String formatSegmentToString(String segmentData) {
        String[] coordinates = segmentData.trim().split(" ");

        return String.format("[ \s(%s , %s) \s (%s , %s) \s]", coordinates[0], coordinates[1], coordinates[2],
                coordinates[3]);
    }

    private void addToVbox(VBox vbox, int segmentId, String segmentText) {
        String text = new String(segmentText);
        HBox textHbox = new HBox();
        HBox.setHgrow(textHbox, Priority.ALWAYS);
        textHbox.setMaxWidth(Double.MAX_VALUE * 0.8);
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(5, 0, 5, 10));
        hbox.setAlignment(Pos.CENTER);
        textHbox.setAlignment(Pos.CENTER_LEFT);
        textHbox.setStyle("-fx-width: 280; -fx-min-width: 280; -fx-max-width: 280;");

        RemoveButton remove = new RemoveButton(segmentId, "-");
        remove.onMouseClickedProperty().set(event -> {
            vbox.getChildren().remove(hbox); // Remove the clicked HBox from its parent VBox
            selectedSegments.removeIf(segment -> segment.getId() == segmentId); // Remove the segment from the list

        });

        hbox.setStyle(
                "-fx-justify: space-between;" +
                        "-fx-background-color: #F4EAD5;" +
                        "-fx-width: 300;" +
                        "-fx-min-width: 300;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0); "

        );
        CustomLabel segmentLabel = new CustomLabel(text);
        segmentLabel.setSegmentStyle();
        textHbox.getChildren().add(segmentLabel);
        hbox.getChildren().addAll(textHbox, remove);
        vbox.getChildren().add(hbox);
    }

}
