import static java.lang.Math.pow;
import static java.lang.StrictMath.sqrt;

/**
 * Created by Gandi on 03/12/2017.
 */
public class Distance {
    public static boolean verticalMove(Position start, Position end) {
        if ((start.x == end.x) || (start.y == end.y)) {
            return false;
        }
        return true;
    }
    public static double calculateDistance(Position start, Position end, boolean estimate) {
        double distance = sqrt(pow(start.x - end.x, 2) + pow(start.y - end.y, 2));
        if (!estimate) {
            return distance;
        }
        double multiply = 1d;
        if (verticalMove(start, end)) {
            multiply = Constants.verticalCost;
        }
        return multiply * distance;
    }
}
