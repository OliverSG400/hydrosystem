package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;

import javax.swing.*;

public class StageResponseTimeWidget implements UpdateAble {

    private JPanel contentPane;
    private JLabel stageTimeLabel;
    private Circuit circuit;

    public StageResponseTimeWidget(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public void updateDataModel(GraphStage mechanismStage) {
        stageTimeLabel.setText(String.valueOf(circuit.stageResponseTime(mechanismStage)));
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
