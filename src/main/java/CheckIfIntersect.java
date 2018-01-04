import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.pow;

/**
 * Created by Gandi on 14/11/2017.
 */
public class CheckIfIntersect {

    static public ArrayList<Pedestrian> checkIfIntersect(Pedestrian pedestrianToCheck, Pedestrian[] pedestriansOnMap) {
        ArrayList<Pedestrian> neighbours = new ArrayList<>();

        for (int i = 0; i < pedestriansOnMap.length; i++) {
            if ((pedestrianToCheck.position.y != pedestriansOnMap[i].position.y) ||
                    pedestrianToCheck.position.x != pedestriansOnMap[i].position.x) {
                /*double distance = Distance.calculateDistance(pedestrianToCheck.position, pedestriansOnMap[i].position, false);
                double radiusSum = pedestrianToCheck.getRadius() + pedestriansOnMap[i].getRadius();
                if (distance <= radiusSum) {
                    neighbours.add(pedestriansOnMap[i]);
                }*/

                neighbours.add(pedestriansOnMap[i]);
            }
        }
        return neighbours;
    }

    static public ArrayList<Element> checkIfIntersectWithObstacles(Pedestrian pedestrianToCheck, ArrayList<Element> obstaclesOnMap) {
        ArrayList<Element> neighbours = new ArrayList<>();

        for (int i = 0; i < obstaclesOnMap.size(); i++) {
            if ((pedestrianToCheck.position.y != obstaclesOnMap.get(i).position.y) ||
                    pedestrianToCheck.position.x != obstaclesOnMap.get(i).position.x) {
                /*double distance = Distance.calculateDistance(pedestrianToCheck.position, obstaclesOnMap.get(i).position, false);
                double radius = pedestrianToCheck.getRadius();
                if (distance <= radius) {
                        neighbours.add(obstaclesOnMap.get(i));
                }*/
                neighbours.add(obstaclesOnMap.get(i));
            }
        }
        return neighbours;
    }
}
