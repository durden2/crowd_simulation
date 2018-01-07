import javax.swing.*;
import java.awt.*;

/**
 * Created by Gandi on 06/01/2018.
 */
public class window  extends JFrame {

        public window() {
            super("Hello World");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            setLocation(50,50);
            setLayout(new FlowLayout());

            add(new JButton("Przycisk 1"));
            add(new JButton("Przycisk 2"));
            add(new JButton("Przycisk 3"));

            setVisible(true);
        }
    }
