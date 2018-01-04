import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
            System.out.print("Path finding...");
            Map ko = new Map();
            ko.setTargetNode(new Element(new Position(750, 150), elementType.EXIT));
            ko.generatePedestrians(50);
            ko.caltulatePaths();
            try {
                new ShowMap().run(ko);
            } catch (InterruptedException e) {
                System.out.println("ERR");
            }
    }
}