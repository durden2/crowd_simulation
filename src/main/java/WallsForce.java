import static java.lang.StrictMath.exp;

/**
 * Created by Gandi on 26/11/2017.
 */
public class WallsForce {
    public static vector2d calculateWallsForce(Pedestrian Apedestrian, Element obstacle) {
        double radius = Apedestrian.getRadius();
        double distance = Distance.calculateDistance(Apedestrian.position, obstacle.position, false);

        /*double nx = (Apedestrian.position.x - obstacle.position.x) / distance;
        double ny = (Apedestrian.position.y - obstacle.position.y) / distance;

        // pixel = cm, need meters to calculate
        radius /= Constants.mapScale;
        distance /= Constants.mapScale;
        nx /= Constants.mapScale;
        ny /= Constants.mapScale;

        vector2d unitVector = vector2d.calculateUnitVector(new vector2d(nx, ny));*/

        radius /= Constants.mapScale;
        distance /= Constants.mapScale;

        vector2d unitVector = vector2d.calculateUnitVector(vector2d.createVectorFromPoints(Apedestrian.position, obstacle.position));

        double exponent = exp((radius - distance) / Constants.distanseOfSocialRepulsiveForce);
        double two = Constants.strengthOfSocialForce * exponent;

        vector2d copyUnitV = unitVector;
        vector2d srf = copyUnitV.multipleByNumber(two);

        double firstElementScalar = Constants.bodyCompressionCoefficient * PhisicalForce.gFunction(radius - distance);

        vector2d copy2UnitV = unitVector;
        vector2d firstElementVectorFinal = copy2UnitV.multipleByNumber(firstElementScalar);

        double secondElement = Constants.coefficientOfSlidingFriction * PhisicalForce.gFunction(radius - distance);

        vector2d tangentialDirection = new vector2d(-1f * unitVector.getY(), unitVector.getX());

        vector2d velocityDifferenceAlongTangential = tangentialDirection.multipleByNumber(vector2d.calculateVectorMagnitude(Apedestrian.getVelocity()));

        vector2d secondElementFinal = velocityDifferenceAlongTangential.multipleByNumber(secondElement);

        vector2d secondForce = firstElementVectorFinal.dodaj(secondElementFinal);

        srf = srf.dodaj(secondForce);
        return srf;
    }
}
