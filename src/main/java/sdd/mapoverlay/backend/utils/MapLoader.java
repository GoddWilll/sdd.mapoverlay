package sdd.mapoverlay.backend.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public ArrayList<String> getMap() {
        return map;
    }
}
