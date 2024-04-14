package sdd.mapoverlay.backend.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;

/**
 * Classe permettant de charger une carte a partir d'un fichier
 */
public class MapLoader {
    private ArrayList<String> map;

    public MapLoader(String filePath) {
        this.map = new ArrayList<>();
        try {
            File mapDataToBeRead = new File(filePath);
            Scanner dataReader = new Scanner(mapDataToBeRead);
            while (dataReader.hasNextLine()) {
                String readData = dataReader.nextLine();
                map.add(readData);

            }
            dataReader.close();
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading map file please select a valid file");
        }
    }

    public ArrayList<String> getMap() {
        return map;
    }
}
