import static java.lang.Math.pow;
import static java.lang.StrictMath.sqrt;

/**
 * Created by Gandi on 14/11/2017.
 */

public class PhisicalForce {
    static double gFunction(double value) {
        if (value < 0) {
            return 0;
        }
        return value;
    }

    static vector2d calculatePhisicalForce(Pedestrian Apedestrian, Pedestrian Bpedestrian) {
        double radiusSum = Apedestrian.getRadius() + Bpedestrian.getRadius();
        double distance = sqrt(pow(Apedestrian.position.x - Bpedestrian.position.x, 2)
                + pow(Apedestrian.position.y - Bpedestrian.position.y, 2));
        double Nijx = (Apedestrian.position.x - Bpedestrian.position.x) / distance;
        double Nijy = (Apedestrian.position.y - Bpedestrian.position.y) / distance;

        double firstElement = Constants.bodyCompressionCoefficient * gFunction(radiusSum - distance);

        Nijx = Nijx * firstElement;
        Nijy = Nijy * firstElement;

        double secontElement = Constants.coefficientOfSlidingFriction * gFunction(radiusSum - distance);

        vector2d srf = new vector2d(Nijx + secontElement, Nijy + secontElement);
        return srf;
    }
}
