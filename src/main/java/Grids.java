import javafx.geometry.Pos;

import java.util.ArrayList;

/**
 * Created by Gandi on 05/12/2017.
 */
public class Grids {

    public static int getPercent (int point, boolean height) {
        if (height) {
            return (Constants.mapHeight * point) / 100;
        }
        return (Constants.mapWidth * point) / 100;
    }

    public static ArrayList<Element> maze () {
        ArrayList<Element> obstacles = new ArrayList<>();
        for (int i = 0; i < Constants.mapWidth; i++) {
            for (int j = 0; j < Constants.mapHeight; j++) {
                if ((j > getPercent(5, true) && j < getPercent(85, true)) && (i == getPercent(82, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((j > getPercent(15, true) && j < getPercent(85, true)) && (i == getPercent(45, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((j > getPercent(45, true) && j < getPercent(65, true)) && (i == getPercent(80, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((j > getPercent(15, true) && j < getPercent(30, true)) && (i == getPercent(30, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((j > getPercent(60, true) && j < getPercent(90, true)) && (i == getPercent(10, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((j > getPercent(30, true) && j < getPercent(45, true)) && (i == getPercent(10, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((j > getPercent(5, true) && j < getPercent(70, true)) && (i == getPercent(60, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((i > getPercent(10, true) && i < getPercent(70, true)) && (j == getPercent(5, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((i > getPercent(50, true) && i < getPercent(70, true)) && (j == getPercent(70, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((i > getPercent(5, true) && i < getPercent(35, true)) && (j == getPercent(45, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((i > getPercent(15, true) && i < getPercent(55, true)) && (j == getPercent(25, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((i > getPercent(50, true) && i < getPercent(95, true)) && (j == getPercent(55, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }
                if ((i > getPercent(75, true) && i < getPercent(95, true)) && (j == getPercent(5, false))) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    continue;
                }

            }
        }
        return obstacles;
    }

    public static ArrayList<Element> primaryMap () {
        ArrayList<Element> obstacles = new ArrayList<>();
        for (int i = 0; i < Constants.mapWidth; i++) {
            for (int j = 0; j < Constants.mapHeight; j++) {
                if ((i == 350) && (j < 255 || j > 260 )) {
                    obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                }
            }
        }
        return obstacles;
    }

    public static ArrayList<Element> gardlo() {
        ArrayList<Element> obstacles = new ArrayList<>();
        for (int i = 0; i < Constants.mapWidth; i++) {
            for (int j = 0; j < Constants.mapHeight; j++) {
                if (i == 200) {
                    if (j > 300 || j < 285) {
                        obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    }
                }
                if (j == 300 || j == 285) {
                    if (i > 200 && i < 400) {
                        obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    }
                }
                if (i == 400) {
                    if (j > 300 || j < 285) {
                        obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                    }
                }
            }
        }
        return obstacles;
    }

    public static ArrayList<Element> lejek() {
        ArrayList<Element> obstacles = new ArrayList<>();
        for (int i = 0; i < Constants.mapWidth; i++) {
            if ((i > 150 && i < getPercent(73, true))) {
                for (int j = 0; j < Constants.mapHeight; j++) {
                        if (j + 150 == i) {
                            obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                            obstacles.add(new Element(new Position(i, j-1), elementType.OBSTACLE));
                            break;
                        }
                }
            }
            if ((i > 0 && i < getPercent(73, true))) {
                for (int j = Constants.mapHeight; j > 0; j--) {
                    if (j - 150 == Constants.mapHeight - i) {
                        obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                        obstacles.add(new Element(new Position(i+1, j), elementType.OBSTACLE));
                        break;
                    }
                }
            }
                for (int j = Constants.mapHeight; j > 0; j--) {
                    if (j == getPercent(48, true) || j == getPercent(52, true)) {
                        if (i > getPercent(72, true) && i < getPercent(90, true)) {
                            obstacles.add(new Element(new Position(i, j), elementType.OBSTACLE));
                        }
                    }
                }
        }
        return obstacles;
    }
}
