import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    private long window;


    public static void main(String[] args) throws InterruptedException {
        System.out.print("KO");
        Map ko = new Map();
        ko.setTargetNode(new Element(new Position(450, 20), elementType.EXIT));
        ko.generatePedestrians();
        ko.caltulatePaths();
        double timeStamp = 0.050; //seconds
        /*for (int i = 0; i < ko.pedestrians.length; i++) {
            vector2d r = FinalForce.calculateFinalForce(ko.pedestrians[i], ko);
            vector2d element1 = r.divideByNumber(timeStamp);
            vector2d speed = element1.divideByNumber(ko.pedestrians[i].getMass());
            ko.pedestrians[i].setCurrentVelocity(speed);
            System.out.println(ko.pedestrians[i].getVelocity().calculateVectorMagnitude(speed));
        }*/
        // ko.setPedestrian(new Element(new Position(590, 20), elementType.PEDESTRIAN));
        // Element startNode3 = new Element(new Position(350, 450), elementType.PEDESTRIAN);
        // Element endNode = new Element(new Position(250, 450), elementType.EXIT);

        try {
            new ShowMap().run(ko);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}