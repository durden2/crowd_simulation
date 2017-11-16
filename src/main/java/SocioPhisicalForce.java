import static java.lang.Math.pow;
import static java.lang.StrictMath.exp;
import static java.lang.StrictMath.sqrt;

/**
 * Created by Gandi on 14/11/2017.
 */
public class SocioPhisicalForce {
    static public vector2d calculateSocioPhisicalForce(Pedestrian Apedestrian, Pedestrian Bpedestrian) {
        double radiusSum = Apedestrian.getRadius() + Bpedestrian.getRadius();
        double distance = sqrt(pow(Apedestrian.position.x - Bpedestrian.position.x, 2)
                + pow(Apedestrian.position.y - Bpedestrian.position.y, 2));
        double Nijx = (Apedestrian.position.x - Bpedestrian.position.x) / distance;
        double Nijy = (Apedestrian.position.y - Bpedestrian.position.y) / distance;

        double exponent = exp((radiusSum - distance) / Apedestrian.getDistanceOfSocialRepulsiveForce());
        double two = Constants.strengthOfSocialForce * exponent;

        Nijx = Nijx * two;
        Nijy = Nijy * two;

        vector2d srf = new vector2d(Nijx, Nijy);
        return srf;
    }
}
