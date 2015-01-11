package edu.khai.k105.hydrosystem.gui.graph;

import edu.khai.k105.hydrosystem.application.graph.GraphModel;
import edu.khai.k105.hydrosystem.application.graph.GraphPoint;
import edu.khai.k105.hydrosystem.gui.validation.NoCharacterVerifier;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class GraphEditor {

    private JList<GraphPoint> pointsList;
    private JTextField xField;
    private JTextField yField;
    private JButton saveButton;
    private JButton removeButton;
    private JGraph graphPanel;
    private JPanel contentPane;

    public GraphEditor(GraphModel graph) {
        xField.setInputVerifier(NoCharacterVerifier.getInstance());
        yField.setInputVerifier(NoCharacterVerifier.getInstance());
        graphPanel.setModel(graph);
        graphPanel.adaptScale();
        bindListeners();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void bindListeners() {

        graphPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GraphPoint selectedPoint = graphPanel.getPointAt(e.getPoint(), 5);
                if (selectedPoint != null) {
                    int index = graphPanel.getModel().getSeries().get(0).getPoints().indexOf(selectedPoint);
                    pointsList.setSelectedIndex(index);
                } else {
                    graphPanel.getModel().getSeries().get(0).getPoints().add(graphPanel.translate(e.getPoint()));
                    pointsList.setSelectedIndex(graphPanel.getModel().getSeries().get(0).getPoints().size() - 1);
                }
                graphPanel.updateUI();
                pointsList.updateUI();
            }
        });

        ListModel<GraphPoint> model = new AbstractListModel<GraphPoint>() {
            @Override
            public int getSize() {
                return graphPanel.getModel().getSeries().get(0).getPoints().size();
            }

            @Override
            public GraphPoint getElementAt(int index) {
                return graphPanel.getModel().getSeries().get(0).getPoints().get(index);
            }
        };
        pointsList.setModel(model);

        pointsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                GraphPoint point = graphPanel.getModel().getSeries().get(0).getPoints().get(pointsList.getSelectedIndex());
                xField.setText(String.valueOf(point.x));
                yField.setText(String.valueOf(point.y));
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = pointsList.getSelectedIndex();
                graphPanel.getModel().getSeries().get(0).getPoints().remove(index);
                xField.setText("");
                yField.setText("");
                graphPanel.updateUI();
                pointsList.updateUI();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = pointsList.getSelectedIndex();
                float x = Float.parseFloat(xField.getText());
                float y = Float.parseFloat(yField.getText());
                graphPanel.getModel().getSeries().get(0).getPoints().remove(index);
                graphPanel.getModel().getSeries().get(0).getPoints().add(index, new GraphPoint(x, y));
                graphPanel.updateUI();
                pointsList.updateUI();
            }
        });
    }

}
