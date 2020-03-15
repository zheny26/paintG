package entity;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Rhombus extends Polygon {

    public Rhombus(Color borderColor, Point anchor, Point corner, Color fillColor) {

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
        points.add(new Point(anchor.x, 2 * anchor.y - corner.y));
        points.add(new Point(2 * anchor.x - corner.x, anchor.y));
        points.add(new Point(anchor.x, corner.y));
        return points;
    }
}
