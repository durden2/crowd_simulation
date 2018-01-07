import java.util.*;

/**
 * Created by Gandi on 10/11/2017.
 */
public class AStar {
    public ArrayList<Position> getPath(Element node) {
        ArrayList<Position> path = new ArrayList<>();
        Element currentNode = node;
        while (currentNode.getParentNode() != null) {
           path.add(currentNode.position);
           currentNode = currentNode.getParentNode();
        }
        Collections.reverse(path);
        return path;
    }


    public ArrayList<Position> calculatePath(Element startNode, Element endNode, Map map) {
        Heap open = new Heap();
        Heap close = new Heap();

        ArrayList<Position> path = new ArrayList<>();

        open.Add(startNode);

        while (open.Count() > 0) {
            Element currentElement = open.RemoveFirst();
            close.Add(currentElement);

            // path has been found
            if ((endNode.position.equals(currentElement.position))) {
                //System.out.println("Path found");
                return getPath(currentElement);
                //return path;
            }

            ArrayList<Element> neighbours = map.getNeighbours(currentElement);

            // traverse all the neighbours of current node
            for (int i = 0; i < neighbours.size(); i++) {

                // if neighbour is not traversable or is in closed list skip to the next one

                if ((neighbours.get(i).elementTypeVariable == elementType.OBSTACLE) || close.Contains(neighbours.get(i))) {
                    continue;
                }

                Element currentNeighbour = neighbours.get(i);

                double newCostToNeighbour = currentElement.getGcost() + Distance.calculateDistance(currentElement.position, currentNeighbour.position, false);

                boolean isInOpenSet = open.Contains(currentNeighbour);
                if ((newCostToNeighbour < currentNeighbour.getGcost()) || !isInOpenSet) {

                    currentNeighbour.setHcost(Distance.calculateDistance(currentNeighbour.position, endNode.position, true));
                    currentNeighbour.setGcost(newCostToNeighbour);
                    currentNeighbour.setParentNode(currentElement);

                    // is its not in an open list add to open list
                    if (!isInOpenSet) {
                        open.Add(currentNeighbour);
                        open.UpdateItem(currentNeighbour);
                        //path.add(currentNeighbour.position);
                    }
                }
            }
        }
        return null;
    }
}
