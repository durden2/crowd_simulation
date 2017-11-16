import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    private long window;


    public static void main(String[] args) {
        System.out.print("KO");
        Map ko = new Map();
        ko.generatePedestrians();
        ko.setTargetNode(new Element(new Position(450, 20), elementType.EXIT));
        ko.caltulatePaths();
        FinalForce ff = new FinalForce();
        for (int i = 0; i < ko.pedestrians.length; i++) {
            vector2d r = ff.calculateFinalForce(ko.pedestrians[i], ko);
            //System.out.println(r.getX());
        }
        // ko.setPedestrian(new Element(new Position(590, 20), elementType.PEDESTRIAN));
        // Element startNode3 = new Element(new Position(350, 450), elementType.PEDESTRIAN);
        // Element endNode = new Element(new Position(250, 450), elementType.EXIT);

        new ShowMap().run(ko);
    }
}