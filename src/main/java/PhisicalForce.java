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
        double distance = Distance.calculateDistance(Apedestrian.position, Bpedestrian.position, true);

        // pixel = cm, need meters to calculate
        radiusSum /= Constants.mapScale;
        distance /= Constants.mapScale;

        vector2d unitVector = vector2d.calculateUnitVector(
                vector2d.createVectorFromPoints(Apedestrian.position, Bpedestrian.position));

        double firstElementScalar = Constants.bodyCompressionCoefficient * gFunction(radiusSum - distance);

        vector2d firstElementVectorFinal = unitVector.multipleByNumber(firstElementScalar);

        double secondElement = Constants.coefficientOfSlidingFriction * gFunction(radiusSum - distance);

        vector2d tangentialDirection = new vector2d(-1f * unitVector.getY(), unitVector.getX());
        vector2d velocityDifference = Bpedestrian.getVelocity().subtact(Apedestrian.getVelocity());
        double velocityDiffScalar = vector2d.calculateVectorMagnitude(velocityDifference);

        vector2d tangentialVelocityDifference = tangentialDirection.multipleByNumber(velocityDiffScalar);

        vector2d secondElementFinal = tangentialVelocityDifference.multipleByNumber(secondElement);

        vector2d srf = firstElementVectorFinal.dodaj(secondElementFinal);
        return srf;
    }
}
