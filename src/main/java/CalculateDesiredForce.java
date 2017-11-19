/**
 * Created by Gandi on 14/11/2017.
 */
public class CalculateDesiredForce {
     static vector2d calculateDesiderForce(Pedestrian pedestrian) {
        // ( mass * (desiredSpeed * directionTowardsDestination) - currentSpeed ) / adaptationTime

         double adaptationTime = 100f;

        double mass = pedestrian.getMass();
        vector2d desiredSpeed = pedestrian.getVelocity();
        vector2d direction = pedestrian.getDesiredDirection();
        vector2d currentSpeed = pedestrian.getCurrentVelocity();

        double scalar = mass * vector2d.calculateVectorMagnitude(desiredSpeed);
        vector2d d = direction.multipleByNumber(scalar);

        vector2d force = d.subtact(currentSpeed).divideByNumber(adaptationTime);

        return force;
    }
}
