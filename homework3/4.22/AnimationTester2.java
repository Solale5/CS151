import javax.swing.*;
import java.awt.*;


public class AnimationTester2 {

    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 100;
    private static final int CAR_WIDTH = 100;

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        MoveableShape shape = new CarShape(0, 0, CAR_WIDTH);
        ShapeIcon icon = new ShapeIcon(shape,
                ICON_WIDTH, ICON_HEIGHT);

        JLabel label = new JLabel(icon);
        frame.setLayout(new FlowLayout());
        frame.add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        final int DELAY = 10;
        // Milliseconds between timer ticks
        Timer t = new Timer(DELAY, event ->
        {
            if (shape.getX() + shape.getWidth() >= ICON_WIDTH) {
                shape.translate(-ICON_WIDTH + shape.getWidth(), 0);
            }
            shape.translate(1, 0);

            label.repaint();
        });
        t.start();
    }

}
