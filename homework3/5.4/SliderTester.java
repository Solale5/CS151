import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderTester {

    //main method
    public static void main(String[] args) {

        JFrame j = new JFrame("Slider Tester");
        CarShape c = new CarShape(j.getX(), j.getY(), 100);
        MySlider slider = new MySlider(c, 100, 800);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slider.s.resize(slider.getValue());
                slider.repaint();
            }
        });
        j.add(slider);
        j.setSize(800, 800);

        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        j.setVisible(true);

    }

    /**
     * my own slider class
     */
    static class MySlider extends JSlider {
        CarShape s;

        /**
         * @param c   a carshape to be drawn
         * @param min minimum slider value
         * @param max max slider value
         */
        public MySlider(CarShape c, int min, int max) {
            super(min, max);
            s = c;


        }


        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            s.draw(g2);
        }

        public void repaint(Rectangle r) {
            super.repaint(r);
        }
    }

}
