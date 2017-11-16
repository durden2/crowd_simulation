import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Gandi on 09/11/2017.
 */
public class Map {
    public Element[][] points;
    public Pedestrian[] pedestrians;
    public Element targetNode;

    public void caltulatePaths() {
        for (int i = 0; i < pedestrians.length; i ++) {
            AStar a = new AStar();
            ArrayList<Position> path = a.calculatePath(pedestrians[i], targetNode, this);
            pedestrians[i].setPath(path);
        }
    }
    public void generatePedestrians() {
        int numberOfPedestrians = 5;
        int mapWidth = 600;
        int mapHeight = 800;
        pedestrians = new Pedestrian[numberOfPedestrians];

        for (int i = 0; i < numberOfPedestrians; i++) {
            int randomX = ThreadLocalRandom.current().nextInt(0, mapWidth + 1);
            int randomY = ThreadLocalRandom.current().nextInt(0, mapHeight + 1);
            points[randomX][randomY].elementTypeVariable = elementType.PEDESTRIAN;
            pedestrians[i] = new Pedestrian();
            Pedestrian temp = new Pedestrian(new Position(randomX, randomY));
            pedestrians[i] = temp;
        }
    }

    public void setTargetNode(Element node) {
        this.targetNode = node;
    }

    public Map() {
        points = new Element[Constants.mapHeight][Constants.mapWidth];
        for (int i = 0; i < Constants.mapHeight; i++) {
            for (int j = 0; j < Constants.mapWidth; j++) {
                if ((j == 110) && (i < 400 || i > 500 )) {
                    points[i][j] = new Element(new Position(i, j), elementType.OBSTACLE);
                } else if ((i == 300) && (j < 300 || j > 320 )) {
                    points[i][j] = new Element(new Position(i, j), elementType.OBSTACLE);
                } else {
                    points[i][j] = new Element(new Position(i, j), elementType.EMPTY);
                }
            }
        }
    };

    public ArrayList<Element> getNeighbours(Element node) {
        ArrayList<Element> neighbours = new ArrayList<Element>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0)
                    continue;

                int nodeX = node.position.x + x;
                int nodeY = node.position.y + y;

                if (nodeX >= 0 && nodeX < Constants.mapHeight && nodeY >= 0 && nodeY < Constants.mapWidth) {
                    neighbours.add(points[nodeX][nodeY]);
                }
            }
        }
        return neighbours;
    }
}
