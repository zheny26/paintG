package entity;


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
        g.setColor(getBorderColor());
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    @Override
    public void move(Point destination) {

    }

    public Point getEnd() {

        return end;
    }

    public void setEnd(Point end) {

        this.end = end;
    }
}
