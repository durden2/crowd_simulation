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

        vector2d unitVector = vector2d.calculateUnitVector(
                vector2d.createVectorFromPoints(Apedestrian.position, Bpedestrian.position));

        double firstElementScalar = Constants.bodyCompressionCoefficient * gFunction(radiusSum - distance);

        vector2d firstElementVectorFinal = unitVector.multipleByNumber(firstElementScalar);

        double secondElement = Constants.coefficientOfSlidingFriction * gFunction(radiusSum - distance);

        vector2d tangentialDirection = new vector2d(-1f * unitVector.getY(), unitVector.getX());
        vector2d velocityDifference = Apedestrian.getVelocity().subtact(Bpedestrian.getVelocity());

        vector2d velocityDifferenceAlongTangential = vector2d.multiplyVectors(velocityDifference, tangentialDirection);

        vector2d secondElementFinal = velocityDifferenceAlongTangential.multipleByNumber(secondElement);

        vector2d srf = firstElementVectorFinal.dodaj(secondElementFinal);
        return srf;
    }
}
