package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Polygon extends Figure2D {

    private List<Point> points;
    private Function<List<Point>, int[]> getX = points -> points.stream().mapToInt(point -> (int) point.getX()).toArray();
    private Function<List<Point>, int[]> getY = points -> points.stream().mapToInt(point -> (int) point.getY()).toArray();

    public Polygon(Color borderColor, Point anchor, Color fillColor,
            List<Point> points) {

        super(borderColor, anchor, fillColor);
        this.points = points;
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(getFillColor());
        int[] xPoints = getX.apply(points);
        int[] yPoints = getY.apply(points);
        int nPoints = points.size();
        g.fillPolygon(xPoints, yPoints, nPoints);
        g.setColor(getFillColor());
        g.drawPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void move(Point destination) {

        Point anchor = getAnchor();
        int deltaX = destination.x - anchor.x;
        int deltaY = destination.y - anchor.y;
        points.forEach(point -> point.translate(deltaX, deltaY));
        super.move(destination);
    }

    @Override
    public boolean contains(Point point) {

        return new java.awt.Polygon(getX.apply(points), getY.apply(points), points.size()).contains(point);
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
