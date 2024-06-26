package sdd.mapoverlay.frontend.Widgets;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import sdd.mapoverlay.backend.segments.Segment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * A class that represents a save file popup dialog.
 */
public class SaveFilePopup {

    /**
     * Displays a save file dialog for the user to enter a file name and saves the
     * segments to a file.
     *
     * @param segmentsToSave the list of segments to save
     */
    public static void showSaveFileDialog(List<Segment> segmentsToSave) {
        // Create the dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Save File");
        dialog.setHeaderText("Enter file name:");

        // Set the button types
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create labels and text field
        Label label = new Label("File Name:");
        TextField fileNameField = new TextField();
        GridPane grid = new GridPane();
        grid.add(label, 1, 1);
        grid.add(fileNameField, 2, 1);

        // Set background color
        grid.setStyle("-fx-background-color: #F0F2EF");

        // Set dialog style
        dialog.getDialogPane().setStyle("-fx-background-color: #F0F2EF");

        dialog.getDialogPane().setContent(grid);

        // Request focus on the text field by default
        fileNameField.requestFocus();

        // Convert the result to a file name-pair when the OK button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(fileNameField.getText(), null);
            }
            return null;
        });

        // Show the dialog and wait for user input
        Optional<Pair<String, String>> result = dialog.showAndWait();

        // Process the result
        result.ifPresent(pair -> {
            String fileName = pair.getKey();
            // Save the file with the entered file name
            if (fileName != null && !fileName.isEmpty()) {
                writeSegmentsToFile(fileName, segmentsToSave);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No file name entered.");
            }
        });
    }

    /**
     * Writes the segments to a file with the given file name.
     *
     * @param fileName       the name of the file to save
     * @param segmentsToSave the list of segments to save
     */
    private static void writeSegmentsToFile(String fileName, List<Segment> segmentsToSave) {
        String directory = "src/main/resources/maps/";
        try {
            FileWriter writer = new FileWriter(directory + fileName + ".txt");

            // Writing each segment to the file
            for (Segment segment : segmentsToSave) {
                writer.write(segment.toString() + "\n");
            }

            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Segments saved to file successfully.");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error writing to file." + e.getMessage());
        }
    }

}
