/**
 * The SideMenu class represents a side menu in a JavaFX application. It provides functionality for entering segments, performing actions on segments, and displaying the segments to scan.
 * 
 * This class extends the VBox class and contains various UI components such as labels, text fields, buttons, and scroll panes.
 * 
 * The SideMenu class includes methods for handling user actions, such as adding segments, clearing segments, plotting segments, and starting the scan.
 * 
 * The class also includes private fields to store information about the segments, intersections, and the current file being edited or loaded.
 * 
 * Usage:
 * SideMenu sideMenu = new SideMenu();
 * 
 * // Add the side menu to the application's layout
 * root.getChildren().add(sideMenu);
 * 
 * // Perform actions on the side menu, such as adding segments or starting the scan
 * sideMenu.addButton.setOnAction(event -> {
 *     sideMenu.addButtonAction(xCoordStart, yCoordStart, xCoordEnd, yCoordEnd);
 * });
 * 
 * sideMenu.startScanButton.setOnAction(event -> {
 *     sideMenu.startScanButtonAction();
 * });
 */
package sdd.mapoverlay.frontend.Widgets;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sdd.mapoverlay.backend.Logic;
import sdd.mapoverlay.backend.segments.Intersection;
import sdd.mapoverlay.frontend.Components.CustomButton;
import sdd.mapoverlay.frontend.Components.CustomLabel;
import sdd.mapoverlay.frontend.Components.CustomTextField;
import sdd.mapoverlay.frontend.Components.CustomToggleButton;
import sdd.mapoverlay.frontend.Components.RemoveButton;
import sdd.mapoverlay.frontend.Constants.ConstantStyles;
import sdd.mapoverlay.backend.map.Map;
import sdd.mapoverlay.backend.segments.Segment;;

public class SideMenu extends VBox {

    private TranslateTransition showTransition;
    private TranslateTransition hideTransition;
    private boolean isMenuVisible = true;
    private ArrayList<Segment> segmentsToDraw = new ArrayList<>();
    private ArrayList<Segment> selectedSegments = new ArrayList<>();
    ArrayList<Intersection> intersections = new ArrayList<>();
    private File openedFile;
    private double maxX = 0;
    private double maxY = 0;
    private double minX = 0;
    private double minY = 0;
    VBox textSegmentsBox;
    CustomToggleButton showSweepLineButton = new CustomToggleButton("Show Sweep Line");

    CustomToggleButton hideSweepLineButton = new CustomToggleButton("Hide Sweep Line");

    /**
     * Constructs a new SideMenu.
     */

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
        yCoordStart.setPromptText("y1");
        enterSegmentsInputRow1.getChildren().addAll(new CustomLabel("("), xCoordStart, new CustomLabel(";"),
                yCoordStart, new CustomLabel(")"));

        CustomTextField xCoordEnd = new CustomTextField();
        xCoordEnd.setPromptText("x2");
        CustomTextField yCoordEnd = new CustomTextField();
        yCoordEnd.setPromptText("y2");
        enterSegmentsInputRow2.getChildren().addAll(new CustomLabel("("), xCoordEnd, new CustomLabel(";"), yCoordEnd,
                new CustomLabel(")"));

        CustomButton addButton = new CustomButton("Add");
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
        showSweepLineButton.selectedProperty().set(true);
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
        textSegmentsBox = new VBox();
        textSegmentsBox.setSpacing(15);
        textSegmentsBox.setPadding(new Insets(10, 0, 20, 10));
        VBox.setMargin(textSegmentsBox, new Insets(10, 0, 20, 0));

        saveButton.setOnAction(event -> {
            SaveFilePopup.showSaveFileDialog(selectedSegments);
        });

        browseButton.setOnAction(event -> {
            browseButtonAction();
        });
        editButton.setOnAction(event -> {
            editButtonAction();
        });

        clearButton.setOnAction(event -> {
            clearButtonAction();
        });

        addButton.setOnAction(event -> {
            addButtonAction(xCoordStart, yCoordStart, xCoordEnd, yCoordEnd);
        });

        VBox scrollVBox = new VBox();

        CustomLabel segmentsLabel = new CustomLabel("Segments to scan");
        segmentsLabel.setTitleStyle();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(200.0);
        scrollPane.setPrefWidth(200.0);
        scrollPane.setContent(textSegmentsBox);

        CustomButton startScanButton = new CustomButton("Start scan");
        HBox.setHgrow(startScanButton, Priority.ALWAYS);
        startScanButton.setMaxWidth(Double.MAX_VALUE);
        startScanButton.setOnAction(event -> {
            startScanButtonAction();
        });

        plotButton.setOnAction(event -> {
            plotButtonAction();
        });

        scrollVBox.getChildren().addAll(segmentsLabel, scrollPane, actionButtonRow);
        scrollVBox.setSpacing(10);

        getChildren().addAll(enterSegmentsBox, scrollVBox, actionButtonColumn, sweepLineButtonBox, startScanButton);

        this.setSpacing(50);
        setPadding(new Insets(20, 20, 20, 20));
        alignmentProperty().set(javafx.geometry.Pos.TOP_CENTER);

        setBackgroundStyle();

    }

    /**
     * Handles the action when the "Plot" button is clicked.
     * Plots the selected segments on the drawing pane.
     * 
     * @param xCoordStart the x-coordinate of the start point of the segment
     * @param yCoordStart the y-coordinate of the start point of the segment
     * @param xCoordEnd   the x-coordinate of the end point of the segment
     * @param yCoordEnd   the y-coordinate of the end point of the segment
     */
    private void plotButtonAction() {
        if (selectedSegments.size() > 0) {
            segmentsToDraw.clear();
            segmentsToDraw.addAll(selectedSegments);
            CenterStack.drawingPane.clear();
            CenterStack.drawingPane.setSize(maxX - minX, maxY - minY);
            for (Segment segment : segmentsToDraw) {
                CenterStack.drawingPane.addDrawing(segment.getSerie());

            }
            maxX = segmentsToDraw.stream().mapToDouble(segment -> segment.getUpperEndPoint().getX()).max()
                    .getAsDouble();
            maxY = segmentsToDraw.stream().mapToDouble(segment -> segment.getUpperEndPoint().getY()).max()
                    .getAsDouble();
        } else {
            // Inform the user to add segments first
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please add segments first.");
            alert.showAndWait();
        }
    }

    /**
     * Handles the action when the "Start scan" button is clicked.
     * Starts the scan and displays the intersections.
     */
    private void startScanButtonAction() {

        if (selectedSegments.size() > 0) {
            intersections = Logic.findIntersection(segmentsToDraw);

            CenterStack.drawingPane.addIntersection(intersections, showSweepLineButton.isSelected());

            if (showSweepLineButton.isSelected()) {

                CenterStack.drawingPane.addSweepline();
                CenterStack.drawingPane.startSweepAnimation();
            }

        } else {
            // Inform the user to add segments first
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please add segments first.");
            alert.showAndWait();
        }
    }

    /**
     * Handles the action when the "Add" button is clicked.
     * Adds a segment to the side menu.
     * 
     * @param xCoordStart the x-coordinate of the start point of the segment
     * @param yCoordStart the y-coordinate of the start point of the segment
     * @param xCoordEnd   the x-coordinate of the end point of the segment
     * @param yCoordEnd   the y-coordinate of the end point of the segment
     */
    private void addButtonAction(CustomTextField xCoordStart, CustomTextField yCoordStart, CustomTextField xCoordEnd,
            CustomTextField yCoordEnd) {
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
            setMinMax(newSegment);
            String segmentText = formatSegmentToString(new String(x1 + " " + y1 + " " + x2 + " " + y2));

            selectedSegments.add(newSegment);
            addToVbox(textSegmentsBox, newSegment.getId(), segmentText);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the action when the "Browse" button is clicked.
     * Loads segments from a file and displays them in the side menu.
     */
    private void browseButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        openedFile = fileChooser.showOpenDialog(null);
        if (openedFile != null) {
            Map map = new Map(openedFile.getAbsolutePath());
            try {
                List<Segment> segments = map.getSegments();

                for (Segment segment : segments) {
                    setMinMax(segment);
                    String segmentText = formatSegmentToString(segment.toString());
                    addToVbox(textSegmentsBox, segment.getId(), segmentText);
                    selectedSegments.add(segment);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Handles the action when the "Clear" button is clicked.
     * Clears all segments from the side menu and the drawing pane.
     */
    private void clearButtonAction() {
        selectedSegments.clear();
        segmentsToDraw.clear();
        CenterStack.drawingPane.clear();
        textSegmentsBox.getChildren().clear();
        intersections.clear();
        openedFile = null;
    }

    /**
     * Handles the action when the "Edit" button is clicked.
     * Writes the selected segments to the currently opened file.
     */
    private void editButtonAction() {
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

    }

    /**
     * Sets the minimum and maximum x and y values for the segments.
     * 
     * @param segment the segment to check
     */
    private void setMinMax(Segment segment) {
        if (segment.getUpperEndPoint().getY() > maxY) {
            maxY = segment.getUpperEndPoint().getY();
        }
        if (segment.getUpperEndPoint().getX() > maxX) {
            maxX = segment.getUpperEndPoint().getX();
        }
        if (segment.getLowerEndPoint().getY() < minY) {
            minY = segment.getLowerEndPoint().getY();
        }
        if (segment.getLowerEndPoint().getX() < minX) {
            minX = segment.getLowerEndPoint().getX();
        }
    }

    /**
     * Writes the selected segments to a file.
     * 
     * @param file the file to write the segments to
     */
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

    /**
     * Sets the background style of the side menu.
     */
    private void setBackgroundStyle() {
        StringBuilder styleBuilder = new StringBuilder();
        styleBuilder.append("-fx-background-color: ")
                .append(formatColor(ConstantStyles.SECONDARY_COLOR))
                .append("; -fx-justify: center;")
                .append("-fx-min-width: 400px;")
                .append("-fx-max-width: 400px;")
                .append("-fx-min-height: 924px;")
                .append("fx-padding: 10px; ")

        ;

        setStyle(styleBuilder.toString());
    }

    /**
     * Formats a color to a hex string.
     * 
     * @param color the color to format
     * @return a string representing the color in hex format
     */
    private String formatColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    /**
     * Formats a segment string to a desired format.
     * 
     * @param segmentData the segment data to format
     * @return a string representing the segment in the desired format
     */
    private String formatSegmentToString(String segmentData) {
        String[] coordinates = segmentData.trim().split(" ");

        return String.format("[ \s(%s , %s) \s (%s , %s) \s]", coordinates[0], coordinates[1], coordinates[2],
                coordinates[3]);
    }

    /**
     * Adds a segment to the side menu.
     * 
     * @param vbox        the VBox to add the segment to
     * @param segmentId   the ID of the segment
     * @param segmentText the text of the segment
     */
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
