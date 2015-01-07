package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.graph.GraphModel;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.gui.project.graph.JGraph;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class OperatingPointViewer {
    private JPanel contentPane;
    private JPanel propertiesPane;
    private JGraph graphPanel;

    public OperatingPointViewer(GraphModel graphModel, GraphPoint operatingPoint) {
        graphPanel.setModel(graphModel);
        graphPanel.adaptScale();
        graphPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                final float delta = 0.5f;
                int notches = e.getWheelRotation();
                double scale = graphPanel.getScaleModifier();
                if (notches < 0) {
                    scale += delta;
                } else {
                    scale -= delta;
                }
                graphPanel.setScaleModifier(scale);
                graphPanel.updateUI();
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JGraph getGraphPanel() {
        return graphPanel;
    }
}
