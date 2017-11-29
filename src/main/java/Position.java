import javafx.geometry.Pos;

/**
 * Created by Gandi on 09/11/2017.
 */

public class Position {
    public int x;
    public int y;
    public boolean visited;
    public Position(int x_, int y_) {
        x = x_;
        y = y_;
    };

    public Position() {
        x = 0;
        y = 0;
    }

    public boolean equals(Position obj) {
        if (this.x == obj.x && this.y == obj.y) {
            return true;
        }
        return false;
    }

    @Override public int hashCode() { return this.x + this.y; }
}
