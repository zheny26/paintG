package entity;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Segment extends Figure1D {

    private Point end;

    public Segment(Color borderColor, Point anchor, Point end) {

        super(borderColor, anchor);
        this.end = end;
    }

    @Override
    public void draw(Graphics2D g) {

        Point start = getAnchor();
        g.setStroke(new BasicStroke(WIDTH));
        g.setColor(getBorderColor());
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    @Override
    public void move(Point destination) {

        Point anchor = getAnchor();
        setEnd(new Point(end.x + destination.x - anchor.x, end.y + destination.y - anchor.y));
        super.move(destination);
    }

    @Override
    public boolean contains(Point point) {

        Point anchor = getAnchor();
        int a = end.y - anchor.y;
        int b = end.x - anchor.x;
        return point.distance(getEnd()) + point.distance(anchor) - Math.sqrt(a * a + b * b) < 1;
    }

    public Point getEnd() {

        return end;
    }

    public void setEnd(Point end) {

        this.end = end;
    }
}
