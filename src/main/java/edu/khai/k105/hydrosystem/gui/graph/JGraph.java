package edu.khai.k105.hydrosystem.gui.graph;

import edu.khai.k105.hydrosystem.dataModel.graph.GraphModel;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphPoint;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphSeries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;

public class JGraph extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final int GRID_MARGIN_BOTTOM = 20;
    private static final int GRID_MARGIN_LEFT = 20;
    private static final int GRID_MARGIN_RIGHT = 20;
    private static final int GRID_MARGIN_TOP = 20;
    private static final Color HIGHLIGHTED_SERIES = Color.BLUE;
    private static final Color HIGHLIGHTED_POINT = Color.RED;
    private static final Color REGULAR_DOT_COLOR = Color.BLACK;
    private double generalScaleModifier = 1;
    private double xScaleModifier = 1;
    private double yScaleModifier = 1;
    private Point shiftModifier = new Point(0, 0);
    private GraphModel model;
    private boolean adaptScale;
    private int selectedSeries = 0;
    private int selectedPoint = 0;
    private List<GraphPoint> infiniteVerticalLine = new ArrayList<>();

    //drug and drop points

    //highlight existent points by mouse click

    //deleting highlighted points by [DEL] key


    public JGraph() {
        addMouseListener(new MouseAdapter() {
            private Point beforeShifting;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    beforeShifting = e.getPoint();

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    Point current = getShiftModifier();
                    Point delta = new Point(e.getPoint().x - beforeShifting.x
                            , e.getPoint().y - beforeShifting.y);
                    Point shifted = new Point(current.x + delta.x, current.y - delta.y);
                    setShiftModifier(shifted);
                    updateUI();
                }
            }

        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                final float delta = 0.1f;
                int notches = e.getWheelRotation();
                if (notches < 0) {
                    generalScaleModifier *= 1 + delta;
                } else {
                    generalScaleModifier *= 1 - delta;
                }
                updateUI();
            }
        });

    }

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
        selectedSeries = model.getSeries().size() - 1;
        selectedPoint = model.getSeries().get(selectedSeries).getPoints().size() - 1;
        infiniteVerticalLine.clear();
    }

    public Point translate(GraphPoint point) {
        if (point != null) {
            Point canvasPoint = new Point();
            canvasPoint.x = (int) (Math.round(point.x * getXScale()) + GRID_MARGIN_LEFT + shiftModifier.x);
            canvasPoint.y = (int) (getHeight() - GRID_MARGIN_BOTTOM - Math.round(point.y * getYScale()) - shiftModifier.y);
            return canvasPoint;
        }
        return null;
    }

    public GraphPoint translate(Point point) {
        if (point != null) {
            GraphPoint graphPoint = new GraphPoint();
            graphPoint.x = ((point.x - GRID_MARGIN_LEFT - shiftModifier.x) / getXScale());
            graphPoint.y = ((getHeight() - GRID_MARGIN_BOTTOM - point.y - shiftModifier.y) / getYScale());
            return graphPoint;
        }
        return null;
    }

    public Point getShiftModifier() {
        return shiftModifier;
    }

    public void setShiftModifier(Point shiftModifier) {
        this.shiftModifier = shiftModifier;
    }

    public double getXScale() {
        return xScaleModifier * generalScaleModifier;
    }

    public double getYScale() {
        return yScaleModifier * generalScaleModifier;
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

    public void setInfiniteVerticalLine(GraphPoint graphPoint) {
        infiniteVerticalLine.add(graphPoint);
    }

    private boolean pointIntersect(Point p1, Point p2, int sensitivityRange) {
        return (Math.abs(p1.x - p2.x) <= sensitivityRange)
                && (Math.abs(p1.y - p2.y) <= sensitivityRange);
    }

    private void calculateScale() {
        if (model.getSeries().get(0).getPoints().size() >= 2) {
            double minX = Integer.MAX_VALUE;
            double minY = Integer.MAX_VALUE;
            double maxX = Integer.MIN_VALUE;
            double maxY = Integer.MIN_VALUE;
            for (GraphSeries series : model.getSeries()) {
                for (GraphPoint point : series.getPoints()) {
                    if (point.x < minX) {
                        minX = point.x;
                    }
                    if (point.y < minY) {
                        minY = point.y;
                    }
                    if (point.x > maxX) {
                        maxX = point.x;
                    }
                    if (point.y > maxY) {
                        maxY = point.y;
                    }
                }
            }
            double graphWidth = maxX - minX;
            double graphHeight = maxY - minY;
            int workingWidth = (getWidth() - GRID_MARGIN_LEFT - GRID_MARGIN_RIGHT - 40);
            int workingHeight = (getHeight() - GRID_MARGIN_TOP - GRID_MARGIN_BOTTOM - 40);
            if(graphHeight > graphWidth) {
                if (graphWidth != 0) {
                    yScaleModifier = graphWidth / graphHeight;
                }
            } else {
                if (graphHeight != 0) {
                    xScaleModifier = graphHeight / graphWidth;
                }
            }
            int canvasMax = Math.max(workingWidth, workingHeight);
            double graphMax = Math.max(graphWidth * xScaleModifier, graphHeight * yScaleModifier);
            generalScaleModifier = canvasMax / graphMax;
            setShiftModifier(new Point(-(int) Math.round(minX * getXScale()),
                    -(int) Math.round(minY * getYScale() ))); //- getHeight()/2
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
        paintStatus(g);
        paintInfiniteLines(g);
    }

    private void paintInfiniteLines(Graphics g) {
        for (GraphPoint point : infiniteVerticalLine) {
            drawLine(g,
                    new Point(translate(point).x, GRID_MARGIN_TOP),
                    new Point(translate(point).x, getHeight() - GRID_MARGIN_BOTTOM));
        }
    }

    private void paintStatus(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        Point startPoint = new Point(getWidth() - 120, 40);
        g.drawString("Scale", startPoint.x, startPoint.y);
        g.drawString(": " + String.format("%.4f", generalScaleModifier), startPoint.x + 40, startPoint.y);
        g.drawString("x factor", startPoint.x, startPoint.y + 10);
        g.drawString(": " + String.format("%.4f", xScaleModifier), startPoint.x + 40, startPoint.y + 10);
        g.drawString("y factor", startPoint.x, startPoint.y + 20);
        g.drawString(": " + String.format("%.4f", yScaleModifier), startPoint.x + 40, startPoint.y + 20);
        g.drawString("x shift", startPoint.x, startPoint.y + 30);
        g.drawString(": " + shiftModifier.x, startPoint.x + 40, startPoint.y + 30);
        g.drawString("y shift", startPoint.x, startPoint.y + 40);
        g.drawString(": " + shiftModifier.y, startPoint.x + 40, startPoint.y + 40);
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
        g.drawString("Па", 3, 30);
        g.drawString("м³", getWidth() - 30, getHeight() - 5);
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
        if (stepPx * generalScaleModifier > 5) {
            double step = stepPx * generalScaleModifier;
            Stroke previousStroke = ((Graphics2D)g).getStroke();
            Stroke dashed = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
            ((Graphics2D)g).setStroke(dashed);
            double tempX = origin.x + step;
            while (tempX < xEnd.x) {
                g.drawLine((int)Math.round(tempX), origin.y, (int)Math.round(tempX), yEnd.y);
                tempX += step;
            }
            double tempY = origin.y - step;
            while (tempY > yEnd.y) {
                g.drawLine(origin.x, (int)Math.round(tempY), xEnd.x, (int)Math.round(tempY));
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
        if (series.getMeta().get("series color") != null) {
            g.setColor((Color) series.getMeta().get("series color"));
        }
        GraphPoint previousGPoint = null;
        for (GraphPoint gPoint : series.getPoints()) {
            drawLine(g, translate(previousGPoint), translate(gPoint));
            previousGPoint = gPoint;
        }
        g.setColor(REGULAR_DOT_COLOR);
        for (GraphPoint gPoint : series.getPoints()) {
            paintDot(g, gPoint, 5);
        }
        g.setColor(Color.gray);
//        Stroke previousStroke = ((Graphics2D)g).getStroke();
//        Stroke dashed = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
//        ((Graphics2D)g).setStroke(dashed);
//        for (GraphPoint gPoint : series.getPoints()) {
//            Point point = translate(gPoint);
//            drawLine(g, point, new Point(point.x, getHeight() - GRID_MARGIN_BOTTOM));
//            drawLine(g, point, new Point(GRID_MARGIN_LEFT, point.y));
//        }
//        ((Graphics2D)g).setStroke(previousStroke);
        for (GraphPoint gPoint : series.getPoints()) {
            Point point = translate(gPoint);
            g.drawString(String.format("%.4f", gPoint.x), point.x, getHeight() - GRID_MARGIN_BOTTOM + 13);
            g.drawString(String.format("%.4f", gPoint.y), point.x + 3, point.y - 3);
            g.drawString(String.format("%.4f", gPoint.y), GRID_MARGIN_LEFT + 3, point.y - 3);
        }
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
