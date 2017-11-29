import java.util.ArrayList;

/**
 * Created by Gandi on 14/11/2017.
 */

public class Pedestrian extends Element {
    private float radius;
    private float mass;
    private vector2d velocity;
    private vector2d currentVelocity;
    public int indexVisited;
    public boolean finished;
    private vector2d desiredDirection;
    public vector2d velocitySum;
    private float distanceOfSocialRepulsiveForce = Constants.distanseOfSocialRepulsiveForce;
    public Position desiredPosition;
    private ArrayList<Position> path = new ArrayList<>();


    public vector2d getDesiredDirection() {
        return desiredDirection;
    }

    public void setDesiredDirection(vector2d desiredDirection) {
        this.desiredDirection = desiredDirection;
    }

    public vector2d getCurrentVelocity() {
        return currentVelocity;
    }

    public void setCurrentVelocity(vector2d currentVelocity) {
        this.currentVelocity = currentVelocity;
    }

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

    public float getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public Pedestrian() {
        this.elementTypeVariable = elementType.PEDESTRIAN;
    }

    public Pedestrian(Position position, vector2d desiredDirection_) {
        this.position = position;
        this.velocity = new vector2d(1,1.3);
        // cmeters
        this.radius = 35f;
        this.elementTypeVariable = elementType.PEDESTRIAN;
        this.desiredDirection = desiredDirection_;
        this.currentVelocity = new vector2d(1,1);
        this.mass = 80;
        this.indexVisited = 0;
        this.finished = false;
        this.velocitySum = new vector2d(0,0);
    }
}
