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

        radius /= 100;
        distance /= 100;
        nx /= 100;
        ny /= 100;
        double exponent = exp((radius - distance) / Apedestrian.getDistanceOfSocialRepulsiveForce());
        double two = Constants.strengthOfSocialForce * exponent;

        vector2d srf = new vector2d(nx * two, ny * two);


        vector2d unitVector = vector2d.calculateUnitVector(
                vector2d.createVectorFromPoints(Apedestrian.position, obstacle.position));

        double firstElementScalar = Constants.bodyCompressionCoefficient * PhisicalForce.gFunction(radius - distance);

        vector2d firstElementVectorFinal = unitVector.multipleByNumber(firstElementScalar / 5);

        double secondElement = Constants.coefficientOfSlidingFriction * PhisicalForce.gFunction(radius - distance);

        vector2d tangentialDirection = new vector2d(-1f * unitVector.getY(), unitVector.getX());
        vector2d velocityDifference = Apedestrian.getVelocity();

        vector2d velocityDifferenceAlongTangential = vector2d.multiplyVectors(velocityDifference, tangentialDirection);

        vector2d secondElementFinal = velocityDifferenceAlongTangential.multipleByNumber(secondElement / 5);

        vector2d secondForce = firstElementVectorFinal.dodaj(secondElementFinal);

        if (secondForce.getX() > 5) {
            System.out.print("BIG!");
        }
        srf = srf.dodaj(secondForce);
        return srf;
    }
}
