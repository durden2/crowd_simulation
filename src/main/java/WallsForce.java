import static java.lang.Math.pow;
import static java.lang.StrictMath.exp;
import static java.lang.StrictMath.sqrt;

/**
 * Created by Gandi on 26/11/2017.
 */
public class WallsForce {
    public static vector2d calculateWallsForce(Pedestrian Apedestrian, Element obstacle) {
        double radius = Apedestrian.getRadius();
        double distance = sqrt(pow(Apedestrian.position.x - obstacle.position.x, 2)
                + pow(Apedestrian.position.y - obstacle.position.y, 2));

        double nx = (Apedestrian.position.x - obstacle.position.x) / distance;
        double ny = (Apedestrian.position.y - obstacle.position.y) / distance;

        // pixel = cm, need meters to calculate
        radius /= 100;
        distance /= 100;
        nx /= 100;
        ny /= 100;

        double exponent = exp((radius - distance) / Constants.distanseOfSocialRepulsiveForce);
        double two = Constants.strengthOfSocialForce * exponent;

        vector2d srf = new vector2d(nx * two, ny * two);


        vector2d unitVector = vector2d.calculateUnitVector(
                vector2d.createVectorFromPoints(Apedestrian.position, obstacle.position));

        double firstElementScalar = Constants.bodyCompressionCoefficient * PhisicalForce.gFunction(radius - distance);

        vector2d firstElementVectorFinal = unitVector.multipleByNumber(firstElementScalar);

        double secondElement = Constants.coefficientOfSlidingFriction * PhisicalForce.gFunction(radius - distance);

        vector2d tangentialDirection = new vector2d(-1f * unitVector.getY(), unitVector.getX());
        vector2d velocityDifference = vector2d.multiplyVectors(Apedestrian.getVelocity(), tangentialDirection);

        vector2d velocityDifferenceAlongTangential = vector2d.multiplyVectors(velocityDifference, tangentialDirection);

        vector2d secondElementFinal = velocityDifferenceAlongTangential.multipleByNumber(secondElement);

        vector2d secondForce = firstElementVectorFinal.dodaj(secondElementFinal);

        srf = srf.dodaj(secondForce);
        return srf;
    }
}
