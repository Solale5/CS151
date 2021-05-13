import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DayVisualizer extends JPanel implements ChangeListener {
    LocalDate cal;
    MyCalendar mc;

    public DayVisualizer(LocalDate d, MyCalendar tmc) {
        cal = d;
        mc = tmc;
        setBackground(Color.white);

    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        JLabel jl0 = new JLabel(cal.getMonth() + " " + cal.getDayOfMonth() + ", " + cal.getYear());


        ArrayList<String> s = mc.getEventsOnGivenDayAsList(mc.dl);
        g2.drawString(jl0.getText(), getWidth() / 2, getHeight() - 600);
        int i = 0;
        for (String e : s) {
            g2.drawString(e, getWidth() / 13, (getHeight() - 200) + (i * 25));
            i++;
        }


    }

    public void repaint(Rectangle r) {
        super.repaint(r);
    }

    public void stateChanged(ChangeEvent e) {
        cal = mc.dl;
        repaint();
    }
}
