import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.StrictMath.sqrt;

/**
 * Created by Gandi on 10/11/2017.
 */
public class vector2d {
    private double x;
    private double y;

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x_) {
        x = x_;
    }

    public void setY(double y_) {
        y = y_;
    }

    vector2d() {
        x = 0;
        y = 0;
    }

    vector2d(double x_, double y_) {
        x = x_;
        y = y_;
    }

    vector2d dodaj(vector2d v) {
        return new vector2d(v.getX() + this.x, v.getY() + this.y);
    }

    vector2d multipleByNumber(double number) {
        return new vector2d(this.x * number, this.y * number);
    }

    vector2d subtact(vector2d v) {
        return new vector2d(v.getX() - this.x, v.getY() - this.y);
    }

    vector2d divideByNumber(double number) {
        return new vector2d(this.x / number, this.y / number);
    }

    static double calculateVectorMagnitude(vector2d v) {
        return sqrt(pow(v.getX(), 2) + pow(v.getY(), 2));
    }

    static vector2d calculateUnitVector (vector2d v1) {
        double magnitude = calculateVectorMagnitude(v1);
        return new vector2d(v1.getX() / magnitude, v1.getY() / magnitude);
    }

    static vector2d createVectorFromPoints(Position p1, Position p2) {
        double xDirection = p2.x - p1.x;
        double yDirection = p2.y - p1.y;
        return new vector2d(xDirection, yDirection);
    }

    static vector2d multiplyVectors(vector2d v1, vector2d v2) {
        return new vector2d(v1.getY() * v2.getY(), v1.getY() * v2.getY());
    }
}
