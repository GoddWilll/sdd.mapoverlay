package sdd.mapoverlay;

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.Point;
import sdd.mapoverlay.backend.utils.MapLoader;
import sdd.mapoverlay.backend.map.Map;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Map map = new Map("fichier1.txt");
        System.out.println(map.getSegments());
    }
}
