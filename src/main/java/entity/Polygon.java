package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Polygon extends Figure2D {

    private List<Point> points;

    public Polygon(Color borderColor, Point anchor, Color fillColor,
            List<Point> points) {

        super(borderColor, anchor, fillColor);
        this.points = points;
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(getFillColor());
        int[] xPoints = points.stream().mapToInt(point -> (int) point.getX()).toArray();
        int[] yPoints = points.stream().mapToInt(point -> (int) point.getY()).toArray();
        int nPoints = points.size();
        g.fillPolygon(xPoints, yPoints, nPoints);
        g.setColor(getFillColor());
        g.drawPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void move(Point destination) {

    }

    public List<Point> getPoints() {

        return points;
    }

    public void setPoints(List<Point> points) {

        this.points = points;
    }

    public void addPoint(Point point) {

        if (points == null) {
            points = new ArrayList<>();
        }
        points.add(point);
    }

    public void setLastPoint(Point point) {

        points.get(points.size() - 1).setLocation(point);
    }
}
