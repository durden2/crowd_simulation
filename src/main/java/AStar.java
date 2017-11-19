import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Gandi on 10/11/2017.
 */
public class AStar {

    public int getDistance(Position startNode, Position endNode) {
        float neighbourX = startNode.x;
        float neighbourY = startNode.y;
        float currentElementX = endNode.x;
        float currentElementY = endNode.y;

        double distance = sqrt((pow(neighbourX - currentElementX, 2) + pow((neighbourY - currentElementY), 2)));
        return (int)distance;
    }

    public ArrayList<Position> getPath(Element node, Map map) {
        ArrayList<Position> path = new ArrayList<Position>();
        Element currentNode = node;
        while (currentNode.getParentNode() != null) {
           path.add(currentNode.position);
           currentNode = currentNode.getParentNode();
        }
        Collections.reverse(path);
        for (int i = 0; i < path.size(); i++){
            System.out.print("X: " + path.get(i).x + " Y: " + path.get(i).y + " ");
        }
        return path;
    }

    public Element findObjectWithLowestFCost(ArrayList<Element> open) {
        Element node = open.get(0);
        for (int i = 1; i < open.size(); i++) {
            if (open.get(i).getFcost() < node.getFcost() || open.get(i).getFcost() == node.getFcost()) {
                if (open.get(i).getHcost() < node.getHcost()) {
                    node = open.get(i);
                }
            }
        }

        return node;
    }


    public ArrayList<Position> calculatePath(Element startNode, Element endNode, Map map) {
        ArrayList<Element> open = new ArrayList<Element>();
        ArrayList<Element> close = new ArrayList<Element>();

        ArrayList<Position> path = new ArrayList<Position>();

        open.add(startNode);

        while (open.size() > 0) {
            Element currentElement = findObjectWithLowestFCost(open);
            open.remove(currentElement);
            close.add(currentElement);

            // path has been found
            if ((endNode.position.x == currentElement.position.x) && (endNode.position.y == currentElement.position.y)) {
                System.out.println("Path found");
                return getPath(currentElement, map);
            }

            ArrayList<Element> neighbours = map.getNeighbours(currentElement);

            // traverse all the neighbours of current node
            for (int i = 0; i < neighbours.size(); i++) {

                // if neighbour is not travesable or is in closed list skip to the next one

                if ((neighbours.get(i).elementTypeVariable == elementType.OBSTACLE) || close.contains(neighbours.get(i))) {
                    continue;
                }

                Element currentNeighbour = neighbours.get(i);

                double newCostToNeighbour = currentElement.getGcost() + getDistance(currentElement.position, currentNeighbour.position);

                if ((newCostToNeighbour < currentNeighbour.getGcost()) || !open.contains(neighbours.get(i))) {

                    currentNeighbour.setHcost(getDistance(currentNeighbour.position, endNode.position));
                    currentNeighbour.setGcost((int)newCostToNeighbour);
                    currentNeighbour.setParentNode(currentElement);

                    // is its not in an open list add to open list
                    if (!open.contains(currentNeighbour)) {
                        open.add(currentNeighbour);
                    }
                }
            }
        }
        return null;
    }
}
