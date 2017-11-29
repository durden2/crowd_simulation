import java.util.ArrayList;

/**
 * Created by Gandi on 09/11/2017.
 */

enum elementType {
    OBSTACLE,
    PEDESTRIAN,
    EMPTY,
    EXIT,
};

public class Element {
    public Position position;
    public elementType elementTypeVariable;
    private Element parentNode;
    private double gcost;
    private double hcost;
    public int heapIndex;
    public double getFcost() { return gcost + hcost; }

    public Element getParentNode() {
        return parentNode;
    }

    public void setParentNode(Element parentNode) {
        this.parentNode = parentNode;
    }

    public double getGcost() {
        return gcost;
    }

    public void setGcost(double gcost) {
        this.gcost = gcost;
    }

    public double getHcost() {
        return hcost;
    }

    public void setHcost(double hcost) {
        this.hcost = hcost;
    }

    public Element(Position position_, elementType element_) {
      position = position_;
      elementTypeVariable = element_;
    };
    public Element() {
        elementTypeVariable = elementType.EMPTY;
        gcost = 0d;
        hcost = 0d;
        position = new Position();
    }

    public int CompareTo(Element nodeToCompare) {
        int currentStatus = 0;
        if (this.getFcost() < nodeToCompare.getFcost()) {
            currentStatus = 1;
        }
        if (this.getFcost() > nodeToCompare.getFcost()) {
            currentStatus = -1;
        }
        if (this.getHcost() < nodeToCompare.getHcost()){
            currentStatus = 1;
        }
        if (this.getHcost() > nodeToCompare.getHcost()){
            currentStatus = -1;
        }
        return -currentStatus;
    }
};

