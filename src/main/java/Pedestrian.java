import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Gandi on 14/11/2017.
 */

public class Pedestrian extends Element {
    private float radius;
    private float mass;
    private vector2d velocity;
    public int indexVisited;
    public boolean finished;
    private vector2d desiredDirection;
    public vector2d velocitySum;
    public Position desiredPosition;
    private ArrayList<Position> path = new ArrayList<>();


    public vector2d getDesiredDirection() {
        return desiredDirection;
    }

    public void setDesiredDirection(vector2d desiredDirection) {
        this.desiredDirection = desiredDirection;
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
        float randomX = rand.nextFloat() * (1.5f - 0.6f) + 0.6f;
        float randomY = rand.nextFloat() * (1.5f - 0.6f) + 0.6f;
        this.velocity = new vector2d(randomX,randomY);
        // centimeters
        this.radius = 25f;
        this.elementTypeVariable = elementType.PEDESTRIAN;
        this.desiredDirection = desiredDirection_;
        this.mass = 80;
        this.indexVisited = 0;
        this.finished = false;
        this.velocitySum = new vector2d(randomX,randomY);
    }
}
