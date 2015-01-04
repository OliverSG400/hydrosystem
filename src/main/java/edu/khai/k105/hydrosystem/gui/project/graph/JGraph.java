package edu.khai.k105.hydrosystem.gui.project.graph;

import edu.khai.k105.hydrosystem.application.project.graph.GraphModel;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;

import javax.swing.*;
import java.awt.*;

public class JGraph extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final int GRID_MARGIN_BOTTOM = 20;
    private static final int GRID_MARGIN_LEFT = 20;
    private static final int GRID_MARGIN_RIGHT = 20;
    private static final int GRID_MARGIN_TOP = 20;
    private static final Color HIGHLIGHTED_SERIES = Color.BLUE;
    private static final Color HIGHLIGHTED_POINT = Color.ORANGE;
    private float scaleModifier = 1;
    private GraphModel model;
    private boolean adaptScale;
    private int selectedSeries = 0;
    private int selectedPoint = 0;

    //drug and drop points

    //highlight existent points by mouse click

    //deleting highlighted points by [DEL] key

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintModel(g);
    }

    public GraphModel getModel() {
        return model;
    }

    public void setModel(GraphModel model) {
        this.model = model;
    }

    public Point translate(GraphPoint point) {
        if (point != null) {
            Point canvasPoint = new Point();
            canvasPoint.x = Math.round(point.x * scaleModifier) + GRID_MARGIN_LEFT;
            canvasPoint.y = getHeight() - GRID_MARGIN_BOTTOM - Math.round(point.y * scaleModifier);
            return canvasPoint;
        }
        return null;
    }

    public GraphPoint translate(Point point) {
        if (point != null) {
            GraphPoint graphPoint = new GraphPoint();
            graphPoint.x = (point.x - GRID_MARGIN_LEFT) / scaleModifier;
            graphPoint.y = (getHeight() - GRID_MARGIN_BOTTOM - point.y) / scaleModifier;
            return graphPoint;
        }
        return null;
    }

    public float getScaleModifier() {
        return scaleModifier;
    }

    public void setScaleModifier(float scaleModifier) {
        this.scaleModifier = scaleModifier;
    }

    public void adaptScale() {
        adaptScale = true;
    }

    public GraphPoint getPointAt(Point canvasPoint, int sensitivityRange) {
        for (GraphSeries series : model.getSeries()) {
            for (GraphPoint point : series.getPoints()) {
                if (pointIntersect(canvasPoint, translate(point), sensitivityRange)) {
                    selectedPoint = series.getPoints().indexOf(point);
                    selectedSeries = model.getSeries().indexOf(series);
                    return point;
                }
            }
        }
        return null;
    }

    private boolean pointIntersect(Point p1, Point p2, int sensitivityRange) {
        return (Math.abs(p1.x - p2.x) <= sensitivityRange)
                && (Math.abs(p1.y - p2.y) <= sensitivityRange);
    }

    private void calculateScale() {
        if (model.getSeries().get(0).getPoints().size() >= 2) {
            float maxX = 0;
            float maxY = 0;
            for (GraphSeries series : model.getSeries()) {
                for (GraphPoint point : series.getPoints()) {
                    if (point.x > maxX) {
                        maxX = point.x;
                    }
                    if (point.y > maxY) {
                        maxY = point.y;
                    }
                }
            }
            int workingWidth = (getWidth() - GRID_MARGIN_LEFT - GRID_MARGIN_RIGHT);
            int workingHeight = (getHeight() - GRID_MARGIN_TOP - GRID_MARGIN_BOTTOM);
            int canvasMax = Math.max(workingWidth, workingHeight);
            float graphMax = Math.max(maxX, maxY);
            setScaleModifier(canvasMax / graphMax);
        }
        adaptScale = false;
    }

    private void paintModel(Graphics g) {
        if (adaptScale) {
            calculateScale();
        }
        setAntialiasint(g);
        clearCanvas(g);
        paintGrid(g);
        if (model != null) {
            for (GraphSeries series : model.getSeries()) {
                paintSeries(g, series);
            }
            paintHighlight(g);
        }
    }

    private void setAntialiasint(Graphics g) {
        ((Graphics2D) g)
        .setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void clearCanvas(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void paintGrid(Graphics g) {
        Point origin = new Point(GRID_MARGIN_LEFT, getHeight() - GRID_MARGIN_BOTTOM);
        Point xEnd = new Point(getWidth() - GRID_MARGIN_RIGHT, getHeight() - GRID_MARGIN_BOTTOM);
        Point yEnd = new Point(GRID_MARGIN_LEFT, GRID_MARGIN_TOP);
        g.setColor(Color.lightGray);
        drawFence(g, origin, xEnd, yEnd, 10);
        g.setColor(Color.BLACK);
        drawLine(g, origin, xEnd);
        drawRightArrow(g, xEnd);
        drawLine(g, origin, yEnd);
        drawTopArrow(g, yEnd);
        drawOriginPoint(g, origin);
    }

    private void drawLine(Graphics g, Point p1, Point p2) {
        if (p1 != null && p2 != null) {
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    private void drawRightArrow(Graphics g, Point point) {
        g.drawLine(point.x, point.y, point.x - 10, point.y - 2);
        g.drawLine(point.x, point.y, point.x - 10, point.y + 2);
    }

    private void drawTopArrow(Graphics g, Point point) {
        g.drawLine(point.x, point.y, point.x - 2, point.y + 10);
        g.drawLine(point.x, point.y, point.x + 2, point.y + 10);
    }

    private void drawOriginPoint(Graphics g, Point origin) {
        int size = 3;
        int half = size / 2;
        g.fillRect(origin.x - half, origin.y - half, size, size);
    }

    private void drawFence(Graphics g, Point origin, Point xEnd, Point yEnd, int stepPx) {
        if (stepPx * scaleModifier > 5) {
            float step = stepPx * scaleModifier;
            Stroke previousStroke = ((Graphics2D)g).getStroke();
            Stroke dashed = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
            ((Graphics2D)g).setStroke(dashed);
            float tempX = origin.x + step;
            while (tempX < xEnd.x) {
                g.drawLine(Math.round(tempX), origin.y, Math.round(tempX), yEnd.y);
                tempX += step;
            }
            float tempY = origin.y - step;
            while (tempY > yEnd.y) {
                g.drawLine(origin.x, Math.round(tempY), xEnd.x, Math.round(tempY));
                tempY -= step;
            }
            ((Graphics2D)g).setStroke(previousStroke);
        }
    }

    private void paintDot(Graphics g, GraphPoint point, int diameter) {
        int radius = diameter / 2;
        Point canvasPoint = translate(point);
        g.fillOval(canvasPoint.x - radius, canvasPoint.y - radius, diameter, diameter);
    }

    private void paintSeries(Graphics g, GraphSeries series) {
        //maybe change color from series meta
        GraphPoint previousGPoint = null;
        for (GraphPoint gPoint : series.getPoints()) {
            drawLine(g, translate(previousGPoint), translate(gPoint));
            previousGPoint = gPoint;
        }
        //maybe paint dots at vertex
    }

    private void paintHighlight(Graphics g) {
        if (model.getSeries().size() > 0) {
            g.setColor(HIGHLIGHTED_SERIES);
            paintSeries(g, model.getSeries().get(selectedSeries));
            if (model.getSeries().get(0).getPoints().size() > 0) {
                g.setColor(HIGHLIGHTED_POINT);
                paintDot(g, model.getSeries().get(selectedSeries).getPoints().get(selectedPoint), 5);
            }
        }
    }

}
