import static java.lang.Math.pow;
import static java.lang.StrictMath.exp;
import static java.lang.StrictMath.sqrt;

/**
 * Created by Gandi on 14/11/2017.
 */
public class SocioPhisicalForce {
    static public vector2d calculateSocioPhisicalForce(Pedestrian Apedestrian, Pedestrian Bpedestrian) {

        // strengh of social repulsive force * exp [ (radii_sum - distance ) / distance_of_social_repulsive_force ] / unit_vector
        // unit vector is pointing from pedestrian j to pedestrian i.

        double radiusSum = Apedestrian.getRadius() + Bpedestrian.getRadius();
        double distance = sqrt(pow(Apedestrian.position.x - Bpedestrian.position.x, 2)
                + pow(Apedestrian.position.y - Bpedestrian.position.y, 2));

        vector2d unitVector = vector2d.calculateUnitVector(
                vector2d.createVectorFromPoints(Apedestrian.position, Bpedestrian.position));

        double exponent = exp((radiusSum - distance) / Apedestrian.getDistanceOfSocialRepulsiveForce());
        double two = Constants.strengthOfSocialForce * exponent;

        vector2d srf = unitVector.multipleByNumber(two);
        return srf;
    }
}
