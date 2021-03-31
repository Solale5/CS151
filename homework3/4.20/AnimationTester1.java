import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 6 This program implements an animation that moves
 * 7 a car shape.
 * 8
 */
public class AnimationTester1 {

    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 200;
    private static final int CAR_WIDTH = 100;

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        MoveableShape shape = new CarShape(0, 0, CAR_WIDTH);
        MoveableShape shape2 = new CarShape(0, 50, CAR_WIDTH);
        MoveableShape shape3 = new CarShape(0, 100, CAR_WIDTH);
        ArrayList<MoveableShape> arr = new ArrayList<>();
        arr.add(shape);
        arr.add(shape2);
        arr.add(shape3);

        ShapeIcon icon = new ShapeIcon(arr, ICON_WIDTH, ICON_HEIGHT);

        JLabel label = new JLabel(icon);
        frame.setLayout(new FlowLayout());
        frame.add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        final int DELAY = 100;
        // Milliseconds between timer ticks
        Timer t = new Timer(DELAY, event ->
        {
            for (MoveableShape s : arr) {
                s.translate(1, 0);
            }
            label.repaint();
        });
        t.start();
    }

}
