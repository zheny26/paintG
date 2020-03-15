package entity;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class RightPolygon extends Polygon {

    public RightPolygon(Color borderColor, Point anchor, Point corner, Color fillColor, int vertexAmount) {

        super(borderColor, anchor, fillColor, new ArrayList<>());
        setPoints(getPoints(corner, vertexAmount));
    }

    private List<Point> getPoints(Point corner, int vertexAmount) {

        List<Point> points = new ArrayList<>(vertexAmount + 1);
        Point anchor = getAnchor();
        double radius = Math.sqrt(Math.pow((corner.x) - anchor.x, 2) + Math.pow(corner.y - anchor.y, 2));
        double z;
        double angle = 360.0 / vertexAmount;

        z = Math.asin((anchor.y - corner.y) / radius) * 180 / Math.PI;
        if (corner.x < anchor.x) {
            z = 180.0 - z;
        }
        for (int i = 0; i < vertexAmount; i++) {
            points.add(new Point(anchor.x + (int) (Math.cos(z / 180 * Math.PI) * radius),
                    anchor.y - (int) (Math.sin(z / 180 * Math.PI) * radius)));
            z = z + angle;
        }
        return points;
    }

    public void setCorner(Point point) {

        setPoints(getPoints(point, super.getPoints().size()));
    }
}
