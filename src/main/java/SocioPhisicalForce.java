import static java.lang.StrictMath.exp;

/**
 * Created by Gandi on 14/11/2017.
 */
public class SocioPhisicalForce {
    static public vector2d calculateSocioPhisicalForce(Pedestrian Apedestrian, Pedestrian Bpedestrian) {

        // strengh of social repulsive force * exp [ (radii_sum - distance ) / distance_of_social_repulsive_force ] / unit_vector
        // unit vector is pointing from pedestrian j to pedestrian i.

        double radiusSum = Apedestrian.getRadius() + Bpedestrian.getRadius();
        double distance = Distance.calculateDistance(Apedestrian.position, Bpedestrian.position, false);

        // pixel = cm, need meters to calculate
        radiusSum /= Constants.mapScale;
        distance /= Constants.mapScale;

        vector2d unitVector = vector2d.calculateUnitVector(vector2d.createVectorFromPoints(Apedestrian.position, Bpedestrian.position));

        double exponetInside = (radiusSum - distance) / Constants.distanseOfSocialRepulsiveForce;
        double exponent = exp(exponetInside);
        double two = Constants.strengthOfSocialForce * exponent;

        vector2d srf = unitVector.multipleByNumber(two);
        return srf;
    }
}
