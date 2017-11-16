import java.util.ArrayList;

/**
 * Created by Gandi on 14/11/2017.
 */

public class Pedestrian extends Element {
    private int radius;
    private int mass;
    private vector2d velocity;
    private float distanceOfSocialRepulsiveForce = 0.8f;
    public Position desiredPosition;
    private ArrayList<Position> path = new ArrayList<>();

    public float getDistanceOfSocialRepulsiveForce() {
        return distanceOfSocialRepulsiveForce;
    }

    public ArrayList<Position> getPath() {
        return path;
    }

    public void setPath(ArrayList<Position> path) {
        this.path = path;
    }

    public vector2d getVelocity() {
        return velocity;
    }

    public void setVelocity(vector2d velocity) {
        this.velocity = velocity;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public Pedestrian() {
        this.elementTypeVariable = elementType.PEDESTRIAN;
    }

    public Pedestrian(Position position) {
        this.position = position;
        this.velocity = new vector2d(1,1);
        this.radius = 20;
        this.elementTypeVariable = elementType.PEDESTRIAN;
    }
}
