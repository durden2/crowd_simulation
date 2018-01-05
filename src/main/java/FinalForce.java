import java.util.ArrayList;

/**
 * Created by Gandi on 14/11/2017.
 */
public class FinalForce {

    static ArrayList<Pedestrian> getNeighbours(Pedestrian[] pedestriansOnMap, Pedestrian currentPedestrian) {
        ArrayList<Pedestrian> neighbours = new ArrayList<>();
        for (int i = 0; i < pedestriansOnMap.length; i++) {
            if ((currentPedestrian.position.y != pedestriansOnMap[i].position.y) ||
                    currentPedestrian.position.x != pedestriansOnMap[i].position.x) {
                neighbours.add(pedestriansOnMap[i]);
            }
        }
        return neighbours;
    }
    static vector2d calculateFinalForce (Pedestrian Apedestrian, Map map) {
        // in fact getting all the pedestrians excluding itself
        ArrayList<Pedestrian> neighbours = getNeighbours(map.pedestrians, Apedestrian);
        vector2d force = new vector2d();
        for (int i = 0; i < neighbours.size(); i++) {
            force = force.dodaj(SocioPhisicalForce.calculateSocioPhisicalForce(Apedestrian, neighbours.get(i)));
            force = force.dodaj(PhisicalForce.calculatePhisicalForce(Apedestrian, neighbours.get(i)));
        }

        force = force.dodaj(CalculateDesiredForce.calculateDesiredForce(Apedestrian, map.targetNode.position));

        ArrayList<Element> obstacles = map.obstacles;

        for (int i = 0; i < obstacles.size(); i++) {
            force = force.dodaj(WallsForce.calculateWallsForce(Apedestrian, obstacles.get(i)));
        }
        return new vector2d(force.getX(), force.getY());
    }
}
