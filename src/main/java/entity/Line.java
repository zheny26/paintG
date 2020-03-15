package entity;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Line extends Ray {

    public Line(Color borderColor, Point anchor, Point end) {

        super(borderColor, anchor, end);
    }

    @Override
    public void draw(Graphics2D g) {

        super.draw(g);
        Point theCenter = getAnchor();
        Point endPoint = getEnd();
        super.setEnd(new Point(2 * theCenter.x - endPoint.x, 2 * theCenter.y - endPoint.y));
        super.draw(g);
    }
}
