package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphModel;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphPoint;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphSeries;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.gui.graph.JGraph;

import javax.swing.*;
import java.awt.*;

public class OperatingPointGraphWidget implements UpdateAble {

    private JPanel contentPane;
    private JGraph graphPanel;
    private Circuit circuit;

    public OperatingPointGraphWidget(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public void updateDataModel(GraphStage mechanismStage) {
        GraphModel graphModel = new GraphModel();
        GraphSeries pumpCharacteristic = circuit.getPump().getPumpCharacteristic();
        pumpCharacteristic.getMeta().put(GraphSeries.SERIES_COLOR, Color.RED);
        GraphSeries systemCharacteristic = circuit.systemCharacteristic(mechanismStage);
        systemCharacteristic.getMeta().put(GraphSeries.SERIES_COLOR, Color.BLUE);
        graphModel.addSeries(pumpCharacteristic);
        graphModel.addSeries(systemCharacteristic);
        GraphPoint operatingPoint = circuit.operatingPoint(mechanismStage);
        if (operatingPoint != null) {
            GraphSeries operatingPointSeries = new GraphSeries();
            operatingPointSeries.addPoint(operatingPoint);
            graphModel.addSeries(operatingPointSeries);
        }
        graphPanel.setModel(graphModel);
        graphPanel.adaptScale();
        graphPanel.updateUI();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
