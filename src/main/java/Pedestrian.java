import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Gandi on 14/11/2017.
 */

public class Pedestrian extends Element {
    private float radius;
    private float mass;
    private vector2d velocity;
    private vector2d desiredVelocity;
    public int indexVisited;
    public boolean finished;
    private vector2d desiredDirection;
    public double distanceLeft;
    private ArrayList<Position> path = new ArrayList<>();
    public boolean stopped;

    public vector2d getDesiredVelocity() {
        return desiredVelocity;
    }

    public void setDesiredVelocity(vector2d desiredVelocity) {
        this.desiredVelocity = desiredVelocity;
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

        Random rand = new Random();
        float randomX = rand.nextFloat() * (Constants.maxPedestrianVelocity - Constants.minPedestrianVelocity) + Constants.minPedestrianVelocity;
        float randomY = rand.nextFloat() * (Constants.maxPedestrianVelocity - Constants.minPedestrianVelocity) + Constants.minPedestrianVelocity;
        this.velocity = new vector2d(randomX, randomY);
        this.desiredVelocity = this.velocity;
        // centimeters
        this.radius = 0.8f;
        this.elementTypeVariable = elementType.PEDESTRIAN;
        this.desiredDirection = desiredDirection_;
        this.mass = 80;
        this.indexVisited = 0;
        this.finished = false;
        this.distanceLeft = 0d;
        this.stopped = false;
    }
}
