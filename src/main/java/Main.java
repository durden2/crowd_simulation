import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
            for (int i = 1; i < 11; i++) {
                System.out.println("Number of pedestrians: " + i * 10);
                System.out.print("Path finding...");
                Map ko = new Map();
                Element targetNode = new Element(new Position(700, 550), elementType.EXIT);
                Element targetNode2 = new Element(new Position(700, 50), elementType.EXIT);
                Element[] targetNodes = new Element[2];
                targetNodes[0] = targetNode;
                targetNodes[1] = targetNode2;
                ko.setTargetNode(targetNodes);
                ko.generatePedestrians(30);
                ko.caltulatePaths();
                try {
                    new ShowMap().run(ko);
                } catch (InterruptedException e) {
                    System.out.println("ERR");
                }
            }
    }
}