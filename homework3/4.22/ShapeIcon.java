import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * An icon that contains a moveable shape.
 */
public class ShapeIcon implements Icon {

    private int width;
    private int height;
    private MoveableShape shape;
    private ArrayList<MoveableShape> ar;

    public ShapeIcon(MoveableShape shape,
                     int width, int height) {
        this.shape = shape;
        this.width = width;
        this.height = height;
        ar = new ArrayList<>();
        ar.add(shape);

    }

    public ShapeIcon(ArrayList<MoveableShape> arr, int width, int height) {
        this.width = width;
        this.height = height;
        ar = new ArrayList<>();
        ar.addAll(arr);
        ar = arr;


    }

    public int getIconWidth() {
        return width;
    }

    public int getIconHeight() {
        return height;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        for (MoveableShape m : ar) {
            m.draw(g2);
        }
//        shape.draw(g2);
    }
}
