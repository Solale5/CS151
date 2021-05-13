import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;


public class CalendarVisualizer extends JPanel implements ChangeListener {
    LocalDate cal;


    MyCalendar mc;


    public CalendarVisualizer(LocalDate theDate, MyCalendar tmc) {


        cal = theDate;

        setSize(400, 400);
        setLayout(new BorderLayout());

        mc = tmc;


        setVisible(true);


    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


    }

    public void repaint() {
        super.repaint();
    }

    public void stateChanged(ChangeEvent e) {

        cal = mc.dl;

        drawMonth();


    }

    public void drawMonth() {

        JPanel p = new JPanel();
        //p.setBackground(Color.gray.darker());
        removeAll();
        revalidate();

        p.repaint();
        p.revalidate();

        p.setLayout(new GridLayout(7, 7, 0, 0));


        p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        add(p);

        p.setLayout(new GridLayout(7, 7, 0, 0));
        String[] daysOfWeek = {"   S", "   M", "   T", "   W", "   R", "   F", "   S"};
        for (String s : daysOfWeek) {

            p.add(getWeekdays(s));

        }
        LocalDate d = cal.withDayOfMonth(1);

        int max = cal.getMonth().maxLength();


        int first = d.getDayOfWeek().getValue();

        for (int i = 0; i < first; i++) {
            Border whiteline = BorderFactory.createLineBorder(Color.white);
            JLabel placeHolder = new JLabel();
            placeHolder.setBorder(whiteline);
            p.add(placeHolder);

        }

        for (int i = 0; i < max + first + 1; i++) {

            if (i > first) {
                JLabel date = new JLabel("" + (i - first));

                int finalI = i - first;
                date.addMouseListener(new MouseListener() {
                    public void mouseClicked(MouseEvent e) {


                    }

                    public void mousePressed(MouseEvent e) {
                        mc.setDay(d.withDayOfMonth(finalI));

                    }

                    public void mouseReleased(MouseEvent e) {

                    }

                    public void mouseEntered(MouseEvent e) {

                    }

                    public void mouseExited(MouseEvent e) {

                    }
                });

                Border whiteline = BorderFactory.createLineBorder(Color.white);
                date.setBorder(whiteline);
                if (finalI == mc.dl.getDayOfMonth()) {
                    Border blackline2 = BorderFactory.createLineBorder(Color.black);
                    date.setBorder(blackline2);
                }
                p.add(date);
            }


        }
        if (max + first < 49) {
            System.out.println("here");

            for (int j = 0; j < 42 - (max + first); j++) {
                Border whiteline = BorderFactory.createLineBorder(Color.white);

                JLabel placeHolder = new JLabel();
                placeHolder.setBorder(whiteline);
                p.add(placeHolder);
            }
        }


        p.revalidate();
        p.setVisible(true);

    }


    public JLabel getWeekdays(String s) {


        JLabel day = new JLabel(s);

        return day;
    }


}
