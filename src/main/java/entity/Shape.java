package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class Shape {

    private Color borderColor;
    private Point anchor;

    public Shape(Color borderColor, Point anchor) {

        this.borderColor = borderColor;
        this.anchor = anchor;
    }

    public abstract void draw(Graphics2D g);

    public abstract void move(Point destination);

    public void setAnchor(Point center) {
        this.anchor = center;
    }

    public Point getAnchor() {
        return anchor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
