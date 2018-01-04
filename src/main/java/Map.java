import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Gandi on 09/11/2017.
 */
public class Map {
    public Element[][] points;
    public Pedestrian[] pedestrians;
    public ArrayList<Element> obstacles;
    public Element targetNode;

    public void caltulatePaths() {
        for (int i = 0; i < pedestrians.length; i ++) {
            AStar a = new AStar();
            ArrayList<Position> path = a.calculatePath(pedestrians[i], targetNode, this);
            pedestrians[i].setPath(path);
        }
    }
    public void generatePedestrians(int numberOfPed) {
        int numberOfPedestrians = numberOfPed;
        pedestrians = new Pedestrian[numberOfPedestrians];

        for (int i = 0; i < numberOfPedestrians; i++) {
            boolean randomPedestrians = true;
            int randomX = 50;
            int randomY = 50 + (i * 20);
            if (randomPedestrians) {
                randomX = ThreadLocalRandom.current().nextInt(5, 250 - 1);
                randomY = ThreadLocalRandom.current().nextInt(5, Constants.mapHeight - 1);
            }
            points[randomX][randomY].elementTypeVariable = elementType.PEDESTRIAN;
            pedestrians[i] = new Pedestrian();
            pedestrians[i] = new Pedestrian();
            vector2d desiredDirection = vector2d.createVectorFromPoints(new Position(randomX, randomY), this.targetNode.position);
            Pedestrian temp = new Pedestrian(new Position(randomX, randomY), desiredDirection);
            pedestrians[i] = temp;
        }
    }

    public void setTargetNode(Element node) {
        this.targetNode = node;
    }

    public int getIndexIfExist(ArrayList<Element> obstacles, int i, int j) {
        for (int ind = 0; ind < obstacles.size(); ind++) {
            if ((obstacles.get(ind).position.x == i) && (obstacles.get(ind).position.y == j)) {
                return ind;
            }
        }
        return -1;
    }

    public Map() {
        points = new Element[Constants.mapWidth][Constants.mapHeight];
        obstacles = new ArrayList<>();
        ArrayList<Element> mapData = Grids.primaryMap();
        for (int i = 0; i < Constants.mapWidth; i++) {
            for (int j = 0; j < Constants.mapHeight; j++) {
                int index = getIndexIfExist(mapData, i, j);
                if (index > -1) {
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

                if (nodeX >= 0 && nodeX < Constants.mapWidth && nodeY >= 0 && nodeY < Constants.mapHeight) {
                    neighbours.add(points[nodeX][nodeY]);
                }
            }
        }
        return neighbours;
    }
}
