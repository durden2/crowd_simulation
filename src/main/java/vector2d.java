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
}
