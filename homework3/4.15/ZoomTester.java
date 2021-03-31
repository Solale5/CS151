import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZoomTester {
    public static void main(String[] args) {

        JFrame jFrame = new JFrame("Car Animation");

        CarShape c = new CarShape(20, 20, 200);
        JLabel p = new MyLabel(c);
        jFrame.add(p);
        jFrame.setSize(1200, 400);
        final int DELAY = 100;
        JButton zoomIn = new JButton("Zoom In");
        JButton zoomOut = new JButton("Zoom Out");

        zoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                c.resize(350);
                p.repaint();
            }
        });
        zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                c.resize(190);
                p.repaint();
            }
        });
        jFrame.add(zoomIn);
        jFrame.add(zoomOut);

        jFrame.setLayout(new GridLayout());

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static class MyLabel extends JLabel {
        MovableShape shape;

        public MyLabel(MovableShape shape) {
            this.shape = shape;

        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            shape.draw(g2);

        }

        public void repaint(Rectangle r) {
            super.repaint(r);
        }
    }
}


