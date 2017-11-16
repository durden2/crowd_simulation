/**
 * Created by Gandi on 14/11/2017.
 */
public class CalculateDesiredForce {
    public int adaptationTime = 100;

    double calculateDesiderForce(double mass, double desiredSpeed, double direction, double speed) {
        // ( mass * (desiredSpeed * direction) - speed ) / adaptationTime
        return (mass * (desiredSpeed * direction) - speed) / adaptationTime;
    }
}
