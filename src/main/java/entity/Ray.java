package entity;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class Ray extends Segment {

    public Ray(Color borderColor, Point anchor, Point end) {

        super(borderColor, anchor, end);
    }

    @Override
    public void setEnd(Point end) {

        Point center = getAnchor();
        double deltaX = end.x - center.x;
        double deltaY = end.y - center.y;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Point resultPoint = new Point();
        if (deltaX == 0 && deltaY == 0) {
            setEnd(end);
            return;
        }
        if (Math.abs(deltaX) < Math.abs(deltaY)) {
            double y = deltaY < 0 ? 0 : dimension.getHeight();
            resultPoint.setLocation(+center.x + deltaX / deltaY * (y - center.y), y);
        } else {
            double x = deltaX < 0 ? 0 : dimension.getWidth();
            resultPoint.setLocation(x, +center.y + deltaY / deltaX * (x - center.x));
        }
        super.setEnd(resultPoint);
    }
}
