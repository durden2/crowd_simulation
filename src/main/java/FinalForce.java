import java.util.ArrayList;

/**
 * Created by Gandi on 14/11/2017.
 */
public class FinalForce {

    static vector2d calculateFinalForce (Pedestrian Apedestrian, Map map) {
        ArrayList<Pedestrian> neightbours = CheckIfIntersect.checkIfIntersect(Apedestrian, map.pedestrians);

        vector2d force = new vector2d();
        for (int i = 0; i < neightbours.size(); i++) {
            force = force.dodaj(SocioPhisicalForce.calculateSocioPhisicalForce(Apedestrian, neightbours.get(i)));
            force = force.dodaj(PhisicalForce.calculatePhisicalForce(Apedestrian, neightbours.get(i)));
            force = force.dodaj(CalculateDesiredForce.calculateDesiderForce(Apedestrian));
        }
        return new vector2d(force.getX(), force.getY());
    }
}
