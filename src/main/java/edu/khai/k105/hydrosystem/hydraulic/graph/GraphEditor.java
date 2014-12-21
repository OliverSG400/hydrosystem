package edu.khai.k105.hydrosystem.hydraulic.graph;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GraphEditor {
    private static final Color CONNECTION_LINE_COLOR = Color.BLACK;
    private static final Color POINT_COLOR = Color.BLUE;
    private static final Color HIGHLIGHT_COLOR = Color.RED;
    private JList pointsList;
    private JTextField xField;
    private JTextField yField;
    private JButton saveButton;
    private JButton removeButton;
    private JPanel graphPanel;
    private JPanel contentPane;
    private Graph graph;

    public GraphEditor(Graph graph) {
        this.graph = graph;
        init();



    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void init() {
        pointsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Point point = graph.getPoints().get(pointsList.getSelectedIndex());
                xField.setText(String.valueOf(point.getX()));
                yField.setText(String.valueOf(point.getY()));
                refresh();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = pointsList.getSelectedIndex();
                graph.getPoints().remove(index);
                xField.setText("");
                yField.setText("");
                refresh();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = pointsList.getSelectedIndex();
                float x = Float.parseFloat(xField.getText());
                float y = Float.parseFloat(yField.getText());
                graph.getPoints().remove(index);
                graph.getPoints().add(index, new Point(x, y));
                refresh();
            }
        });

        graphPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                graph.addPoint(e.getPoint().x, e.getPoint().y);
                pointsList.setSelectedIndex(graph.getPoints().size() - 1);
                refresh();
//              Graphics graphics = e.getComponent().getGraphics();
//              graphics.setColor(Color.RED);
//              graphics.drawRect(point.x, point.y, 10, 10);
            }
        });
        bindJListWithGraph();
    }

    private void bindJListWithGraph() {
        ListModel<Point> model = new AbstractListModel<Point>() {
            @Override
            public int getSize() {
                return graph.getPoints().size();
            }

            @Override
            public Point getElementAt(int index) {
                return graph.getPoints().get(index);
            }
        };
        pointsList.setModel(model);
    }

    private void refresh() {
        Graphics2D graphics = (Graphics2D) graphPanel.getGraphics();
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0, graphPanel.getWidth(), graphPanel.getHeight());
        Point previousPoint = null;
        for (Point point : graph.getPoints()) {
            connectPoints(graphics, previousPoint, point);
            previousPoint = point;
        }
        for (Point point : graph.getPoints()) {
            drawPoint(graphics, point);
        }
        Point current = graph.getPoints().get(pointsList.getSelectedIndex());
        highlightPoint(graphics, current);
        pointsList.updateUI();
    }

    private void highlightPoint(Graphics2D graphics, Point current) {
        graphics.setColor(HIGHLIGHT_COLOR);
        int x = Math.round(current.getX());
        int y = Math.round(current.getY());
        int delta = 5;
        graphics.drawRect(x - delta, y - delta, delta * 2, delta * 2);
    }

    private void drawPoint(Graphics graphics, Point point) {
        graphics.setColor(POINT_COLOR);
        int x = Math.round(point.getX());
        int y = Math.round(point.getY());
        int delta = 3;
        graphics.fillOval(x - delta, y - delta, delta * 2 + 1, delta * 2 + 1);
    }

    private void connectPoints(Graphics graphics, Point previous, Point current) {
        if (previous != null) {
            graphics.setColor(CONNECTION_LINE_COLOR);
            int x1 = Math.round(previous.getX());
            int y1 = Math.round(previous.getY());
            int x2 = Math.round(current.getX());
            int y2 = Math.round(current.getY());
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

}
