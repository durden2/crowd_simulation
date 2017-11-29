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
            if ((pedestrianToCheck.position.y != pedestriansOnMap[i].position.y) &&
                    pedestrianToCheck.position.x != pedestriansOnMap[i].position.x) {
                double distanceX = pow(pedestrianToCheck.position.x - pedestriansOnMap[i].position.x, 2);
                double distanceY = pow(pedestrianToCheck.position.y - pedestriansOnMap[i].position.y, 2);
                double radiusSum = pedestrianToCheck.getRadius() + pedestriansOnMap[i].getRadius();
                double radiusSubtract = pedestrianToCheck.getRadius() - pedestriansOnMap[i].getRadius();
                if ((distanceX + distanceY) <= pow(radiusSum, 2)) {
                    if ((distanceX + distanceY) >= pow(radiusSubtract, 2)) {
                        neighbours.add(pedestriansOnMap[i]);
                    }
                }
            }
        }
        return neighbours;
    }

    static public ArrayList<Element> checkIfIntersectWithObstacles(Pedestrian pedestrianToCheck, ArrayList<Element> obstaclesOnMap) {
        ArrayList<Element> neighbours = new ArrayList<>();

        for (int i = 0; i < obstaclesOnMap.size(); i++) {
            if ((pedestrianToCheck.position.y != obstaclesOnMap.get(i).position.y) &&
                    pedestrianToCheck.position.x != obstaclesOnMap.get(i).position.x) {
                double distanceX = pow(pedestrianToCheck.position.x - obstaclesOnMap.get(i).position.x, 2);
                double distanceY = pow(pedestrianToCheck.position.y - obstaclesOnMap.get(i).position.y, 2);
                double radius = pedestrianToCheck.getRadius();
                if ((distanceX + distanceY) <= pow(radius, 2)) {
                        neighbours.add(obstaclesOnMap.get(i));
                    }
                }
            }
        return neighbours;
    }
}
