import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClockTester {

    static class MyFrame extends JFrame {

        public MyFrame() {
            setTitle("Solomon's Clock");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(500, 500);
            ClockIcon clockIcon = new ClockIcon(300);
            ClockPanel c = new ClockPanel(clockIcon);
            add(c);
            ActionListener listener = e -> {

                c.repaint();
            };
            Timer t = new Timer(1000, listener);
            t.start();
            setLayout(new GridLayout());
        }
    }

    static class ClockPanel extends JPanel {
        private Icon icon;


        public ClockPanel(Icon theIcon) {
            icon = theIcon;


        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            icon.paintIcon(this, g, icon.getIconHeight() / 4, icon.getIconHeight() / 4);
        }

        public void repaint(Rectangle r) {
            super.repaint(r);
        }
    }

    public static void main(String[] args) {
        MyFrame m = new MyFrame();
        m.setVisible(true);


    }
}
