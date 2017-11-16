import java.util.ArrayList;

/**
 * Created by Gandi on 14/11/2017.
 */
public class FinalForce {

    vector2d calculateFinalForce (Pedestrian Apedestrian, Map map) {
        ArrayList<Pedestrian> neightbours = CheckIfIntersect.checkIfIntersect(Apedestrian, map.pedestrians);
        System.out.println(neightbours.size());
        double Pxi = Apedestrian.position.x; // X - position
        double Pyi = Apedestrian.position.y;   // Y-position
        double Vxi = Apedestrian.getVelocity().getX();         // X-velocity
        double Vyi = Apedestrian.getVelocity().getY();           // Y-velocity
        double Ri = Apedestrian.getRadius();                    // Agent's radius
        double Mi = Apedestrian.getMass();                   // Agent's mass
        double fijx = 0;    //  X-direction interaction force initialization
        double fijy = 0;    //  Y-direction interaction force initialization

        vector2d force = new vector2d();
        for (int i = 0; i < neightbours.size(); i++) {
            force = force.dodaj(SocioPhisicalForce.calculateSocioPhisicalForce(Apedestrian, neightbours.get(i)));
            force = force.dodaj(PhisicalForce.calculatePhisicalForce(Apedestrian, neightbours.get(i)));
        }
        return new vector2d(force.getX(), force.getY());
    }
}
