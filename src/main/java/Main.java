import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    private long window;


    public static void main(String[] args) throws InterruptedException {
        Map ko = new Map();
        ko.setTargetNode(new Element(new Position(750, 150), elementType.EXIT));
        ko.generatePedestrians();
        ko.caltulatePaths();
        /*for (int i = 0; i < ko.pedestrians.length; i++) {
            vector2d r = FinalForce.calculateFinalForce(ko.pedestrians[i], ko);
            vector2d element1 = r.divideByNumber(timeStamp);
            vector2d speed = element1.divideByNumber(ko.pedestrians[i].getMass());
            ko.pedestrians[i].setCurrentVelocity(speed);
            System.out.println(ko.pedestrians[i].getVelocity().calculateVectorMagnitude(speed));
        }*/

        try {
            new ShowMap().run(ko);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}