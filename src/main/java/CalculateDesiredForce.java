/**
 * Created by Gandi on 14/11/2017.
 */
public class CalculateDesiredForce {
     static vector2d calculateDesidedForce(Pedestrian pedestrian, Position targetNode) {
        // ( mass * (desiredSpeed * directionTowardsDestination) - currentSpeed ) / adaptationTime

         double adaptationTime = 100f;

        double mass = pedestrian.getMass();
        Position currentPosition = pedestrian.position;
        vector2d desiredSpeed = pedestrian.getVelocity();

        vector2d directionOfMotion = vector2d.createVectorFromPoints(currentPosition, targetNode);
        directionOfMotion = directionOfMotion.divideByNumber(vector2d.calculateVectorMagnitude(directionOfMotion));
        vector2d direction = directionOfMotion;
        vector2d currentSpeed = pedestrian.getVelocity();

        double scalar = mass * vector2d.calculateVectorMagnitude(desiredSpeed);
        vector2d d = direction.multipleByNumber(scalar);

        vector2d force = d.subtact(currentSpeed).divideByNumber(adaptationTime);

        return force;
    }
}
