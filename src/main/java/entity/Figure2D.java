package entity;


import java.awt.Color;

public abstract class Figure2D {

    private Color fillColor = new Color(255, 255, 255);

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
}
