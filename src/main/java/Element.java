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
    private int gcost;
    private int hcost;
    public int getFcost() { return gcost + hcost; }

    public Element getParentNode() {
        return parentNode;
    }

    public void setParentNode(Element parentNode) {
        this.parentNode = parentNode;
    }

    public int getGcost() {
        return gcost;
    }

    public void setGcost(int gcost) {
        this.gcost = gcost;
    }

    public int getHcost() {
        return hcost;
    }

    public void setHcost(int hcost) {
        this.hcost = hcost;
    }

    public Element(Position position_, elementType element_) {
      position = position_;
      elementTypeVariable = element_;
    };
    public Element() {
        elementTypeVariable = elementType.EMPTY;
        gcost = 0;
        hcost = 0;
        position = new Position();
    }
};

