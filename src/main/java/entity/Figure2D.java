package entity;


import java.awt.Color;
import java.awt.Point;

public abstract class Figure2D extends Shape {

    private Color fillColor;

    public Figure2D(Color borderColor, Point anchor, Color fillColor) {

        super(borderColor, anchor);
        this.fillColor = fillColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
}
