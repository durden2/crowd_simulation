import static java.lang.Math.pow;
import static java.lang.StrictMath.sqrt;

/**
 * Created by Gandi on 03/12/2017.
 */
public class Distance {
    public static double calculateDistance(Position start, Position end) {
        return sqrt(pow(start.x - end.x, 2) + pow(start.y - end.y, 2));
    }
}
