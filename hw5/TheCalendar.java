import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.time.LocalDate;

class TheCalendar extends JPanel {
    MyCalendar mc;


    public TheCalendar() {
        LocalDate cal = LocalDate.now();
        MyCalendar mc = new MyCalendar();
        setSize(700, 700);

//        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
//
//        for (int i = 0; i < 7; i++) {
//
//            JLabel day = new JLabel("" + daysOfWeek[i] + "");
//            add(day);
//        }

        double first = cal.withDayOfMonth(1).getDayOfWeek().getValue();
        sopl(first);

        for (int i = 0; i < (7 - first); i++) {

            JLabel day = new JLabel("" + (i + 1) + "");
            add(day, (getWidth() / 7) * first, 0);
            sopl((getWidth() / 7) * first);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;


        for (int i = 0; i < 7; i++) {


            if (i < 5) {
                Point2D.Double p1 = new Point2D.Double(0, (double) (getHeight() / 5) * (i + 1));
                Point2D.Double p2 = new Point2D.Double(getWidth(), (double) (getHeight() / 5) * (i + 1));
                Line2D.Double l = new Line2D.Double(p1, p2);
                g2.draw(l);

            }
            Point2D.Double p1 = new Point2D.Double((double) (getWidth() / 7) * (i + 1), 0);
            Point2D.Double p2 = new Point2D.Double((double) (getWidth() / 7) * (i + 1), getHeight());
            Line2D.Double l = new Line2D.Double(p1, p2);
            g2.draw(l);

        }
    }

    public void repaint(Rectangle r) {
        super.repaint(r);
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setSize(700, 500);

        TheCalendar c = new TheCalendar();
        c.setVisible(true);
        jf.add(c);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void sopl(Object x) {
        System.out.println(x);
    }
}
