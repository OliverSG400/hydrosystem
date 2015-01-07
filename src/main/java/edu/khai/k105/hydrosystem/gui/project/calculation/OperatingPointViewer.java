package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.Project;
import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.graph.GraphModel;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;
import edu.khai.k105.hydrosystem.gui.project.graph.JGraph;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class OperatingPointViewer {
    private JPanel contentPane;
    private JPanel propertiesPane;
    private JGraph graphPanel;
    private JLabel operatingPointLabel;
    private JSpinner spinner1;
    private Circuit circuit;

    public OperatingPointViewer(Circuit circuit) {
        this.circuit = circuit;
        displayGraph(0);
    }

    private void displayGraph(int i) {
        GraphModel graphModel = new GraphModel();
        graphModel.addSeries(circuit.getPump().getPumpCharacteristic());
        graphModel.addSeries(circuit.systemCharacteristic());
        GraphPoint operatingPoint = circuit.operatingPoint();
        GraphSeries operatingPointSeries = new GraphSeries();
        operatingPointSeries.addPoint(operatingPoint);
        graphModel.addSeries(operatingPointSeries);
        graphPanel.setModel(graphModel);
        graphPanel.adaptScale();
        operatingPointLabel.setText(operatingPoint.toString());
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JGraph getGraphPanel() {
        return graphPanel;
    }

}
