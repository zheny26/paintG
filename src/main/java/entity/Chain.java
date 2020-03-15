package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class Chain extends Figure1D {

    private List<Segment> segments;

    public Chain(Color borderColor, Point anchor, List<Segment> segments) {

        super(borderColor, anchor);
        this.segments = segments;
    }

    @Override
    public void draw(Graphics2D g) {

        segments.forEach(segment -> segment.draw(g));
    }

    @Override
    public void move(Point destination) {

    }

    public List<Segment> getSegments() {

        return segments;
    }

    public void setSegments(List<Segment> segments) {

        this.segments = segments;
    }

    public void addSegment(Segment segment) {

        if (segments == null) {
            segments = new ArrayList<>();
        }
        segments.add(segment);
    }

    public Segment getLastSegment() {

        return CollectionUtils.isEmpty(segments) ? null : segments.get(segments.size() - 1);
    }
}
