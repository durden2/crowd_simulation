import java.awt.*;

/**
 * Created by Gandi on 14/11/2017.
 */
public class CalculateDesiredForce {
     static vector2d calculateDesidedForce(Pedestrian pedestrian, Position targetNode) {
        // ( mass * (desiredSpeed * directionTowardsDestination) - currentSpeed ) / adaptationTime

        double adaptationTime = 0.5f;

        double mass = pedestrian.getMass();
        Position currentPosition = pedestrian.position;
        double desiredSpeed = vector2d.calculateVectorMagnitude(pedestrian.getDesiredVelocity());

        vector2d directionOfMotion = vector2d.createVectorFromPoints(currentPosition, targetNode);
        directionOfMotion = vector2d.calculateUnitVector(directionOfMotion);
        vector2d currentSpeed = pedestrian.getVelocity();

        vector2d d = directionOfMotion.multipleByNumber(desiredSpeed);

        d = new vector2d(d.getX() - currentSpeed.getX(), d.getY() - currentSpeed.getY());

        vector2d f = d.multipleByNumber(mass);

        f = new vector2d(f.getX() / adaptationTime, f.getY() / adaptationTime);
        return f;
    }
}
