import java.awt.*;

public interface MovableShape {
    void draw(Graphics2D g2);

    void translate(int dx, int dy);
}
