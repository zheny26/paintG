package entity;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Polygon {

    public Rectangle(Color borderColor, Point anchor, Point corner, Color fillColor) {

        super(borderColor, anchor, fillColor, new ArrayList<>());
        setPoints(getPoints(corner));
    }

    public void setCorner(Point corner) {
        setPoints(getPoints(corner));
    }

    private List<Point> getPoints(Point corner) {
        Point anchor = getAnchor();
        List<Point> points = new ArrayList<>(4);
        points.add(new Point(corner.x, anchor.y));
        points.add(anchor);
        points.add(new Point(anchor.x, corner.y));
        points.add(corner);
        return points;
    }
}
