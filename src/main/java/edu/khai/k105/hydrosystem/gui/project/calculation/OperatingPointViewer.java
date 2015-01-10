package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.graph.GraphModel;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;
import edu.khai.k105.hydrosystem.application.project.graph.GraphStage;
import edu.khai.k105.hydrosystem.gui.graph.JGraph;

import javax.swing.*;

public class OperatingPointViewer implements Recalculator{
    private JPanel contentPane;
    private JPanel propertiesPane;
    private JGraph graphPanel;
    private JLabel operatingPointLabel;
    private Pager pager;
    private Circuit circuit;

    public OperatingPointViewer(Circuit circuit) {
        this.circuit = circuit;
        recalculate(pager.getCurrentStageGraph());
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JGraph getGraphPanel() {
        return graphPanel;
    }

    @Override
    public void recalculate(GraphStage mechanismStage) {
        GraphModel graphModel = new GraphModel();
        graphModel.addSeries(circuit.getPump().getPumpCharacteristic());
        graphModel.addSeries(circuit.systemCharacteristic(mechanismStage));
        GraphPoint operatingPoint = circuit.operatingPoint(mechanismStage);
        if (operatingPoint != null) {
            GraphSeries operatingPointSeries = new GraphSeries();
            operatingPointSeries.addPoint(operatingPoint);
            graphModel.addSeries(operatingPointSeries);
            operatingPointLabel.setText(operatingPoint.toString());
        } else {
            operatingPointLabel.setText("отсутствует");
        }
        graphPanel.setModel(graphModel);
        graphPanel.adaptScale();
    }

    private void createUIComponents() {
        pager = new Pager(circuit.getMechanism().getStageGraph(), this);
    }
}
