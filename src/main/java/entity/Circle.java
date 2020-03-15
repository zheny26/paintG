package entity;

import java.awt.Color;
import java.awt.Point;

public class Circle extends Ellipse {

    public Circle(Color borderColor, Point anchor, Color fillColor, Point corner) {

        super(borderColor, anchor, fillColor, corner);
    }

    @Override
    public void setCorner(Point corner) {

        super.setCorner(corner);
        Point anchor = getAnchor();
        super.corner.setLocation(super.corner.x, anchor.y - anchor.x + super.corner.x);
        setWidth(Math.abs(2 * (anchor.x - corner.x)));
        setHeight(Math.abs(2 * (anchor.y - corner.y)));
    }
}
