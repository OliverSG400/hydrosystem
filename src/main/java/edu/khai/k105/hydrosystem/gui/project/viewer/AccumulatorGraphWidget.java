package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.graph.GraphModel;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphSeries;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.gui.graph.JGraph;

import javax.swing.*;
import java.awt.*;

public class AccumulatorGraphWidget implements UpdateAble {

    private JPanel contentPane;
    private JGraph graphPanel;
    private Circuit circuit;

    public AccumulatorGraphWidget(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public void updateDataModel(GraphStage mechanismStage) {
        GraphModel graphModel = new GraphModel();
        GraphSeries pumpCharacteristic = circuit.getPump().getPumpCharacteristic();
        pumpCharacteristic.getMeta().put("series color", Color.RED);

        graphModel.addSeries(pumpCharacteristic);

        graphPanel.setModel(graphModel);
        graphPanel.adaptScale();
        graphPanel.updateUI();
        circuit.accumulatorCalculation(mechanismStage);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
