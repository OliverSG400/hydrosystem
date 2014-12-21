package edu.khai.k105.hydrosystem.hydraulic.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Point> points = new ArrayList<Point>();
    private String xMeasure;
    private String yMeasure;

    public String getyMeasure() {
        return yMeasure;
    }

    public String getxMeasure() {
        return xMeasure;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(float x, float y) {
        points.add(new Point(x, y));
    }

}
