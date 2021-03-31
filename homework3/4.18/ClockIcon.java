import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.GregorianCalendar;

public class ClockIcon implements Icon {
    private int size;


    public ClockIcon(int aSize) {
        this.size = aSize;


    }

    public int getIconWidth() {
        return size;
    }

    public int getIconHeight() {
        return size;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        GregorianCalendar gc = new GregorianCalendar();

        double increment = size / 15;


        Graphics2D g2 = (Graphics2D) g;

        Ellipse2D.Double clock = new Ellipse2D.Double(x, y, size, size);

        Point2D.Double origin = new Point2D.Double(clock.getCenterX(), clock.getCenterY());
        //seconds hand logic
        double secondsx = (Math.cos(((gc.getTime().getSeconds() + 45) * Math.PI) / 30) * ((double) size / 2)) + size - (x);
        double secondsy = (Math.sin(((gc.getTime().getSeconds() + 45) * Math.PI) / 30) * ((double) size / 2)) + size - (x);

        //
        Point2D.Double pointAlongTheCircle = new Point2D.Double(secondsx, secondsy);
        Line2D.Double secondhand = new Line2D.Double(pointAlongTheCircle, origin);

        //minute hand logic
        double minutex = (Math.cos(((gc.getTime().getMinutes() + 45) * Math.PI) / 30) * ((double) size / 3)) + size - (x);
        double minutey = (Math.sin(((gc.getTime().getMinutes() + 45) * Math.PI) / 30) * ((double) size / 3)) + size - (x);

        Point2D.Double pointAlongTheCircleMinute = new Point2D.Double(minutex, minutey);
        Line2D.Double minutehand = new Line2D.Double(pointAlongTheCircleMinute, origin);


        //hour hand logic
        double hourx = (Math.cos(((gc.getTime().getHours() + 9) * Math.PI) / 6) * ((double) size / 5)) + size - (x);
        double houry = (Math.sin(((gc.getTime().getHours() + 9) * Math.PI) / 6) * ((double) size / 5)) + size - (x);

        Point2D.Double pointAlongTheCircleHour = new Point2D.Double(hourx, houry);
        Line2D.Double hourhand = new Line2D.Double(pointAlongTheCircleHour, origin);

       
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(minutehand);
        g2.setStroke(new BasicStroke(2.5f));
        g2.draw(hourhand);
        g2.setStroke(new BasicStroke(0.5f));

        g2.draw(secondhand);
        g2.draw(clock);

    }


}
