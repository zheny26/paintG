package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Ellipse extends Figure2D {

    protected Point corner;
    private int width;
    private int height;

    public Ellipse(Color borderColor, Point anchor, Color fillColor, Point corner) {

        super(borderColor, anchor, fillColor);
        setCorner(corner);
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(getFillColor());
        g.fillOval(corner.x, corner.y, width, height);
        g.setColor(getBorderColor());
        g.drawOval(corner.x, corner.y, width, height);
    }

    @Override
    public void move(Point destination) {
        corner.translate(destination.x - getAnchor().x, destination.y - getAnchor().y);
        super.move(destination);
    }

    @Override
    public boolean contains(Point point) {

        return new Ellipse2D.Double(corner.x, corner.y, width, height).contains(point);
    }

    public int getWidth() {

        return width;
    }

    public void setWidth(int width) {

        this.width = width;
    }

    public int getHeight() {

        return height;
    }

    public void setHeight(int height) {

        this.height = height;
    }

    public Point getCorner() {

        return corner;
    }

    public void setCorner(Point corner) {

        this.corner = corner;
        Point anchor = getAnchor();
        int deltaX = anchor.x - corner.x;
        int deltaY = anchor.y - corner.y;
        if (deltaX < 0) {
            corner.translate(2 * deltaX, 0);
        }
        if (deltaY < 0) {
            corner.translate(0, 2 * deltaY);
        }
        width = Math.abs(2 * (anchor.x - corner.x));
        height = Math.abs(2 * (anchor.y - corner.y));
    }
}


