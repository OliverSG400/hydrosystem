package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;

import javax.swing.*;

public class TotalResponseTimeWidget implements UpdateAble {

    private JLabel totalResponseTimeLabel;
    private JPanel contentPane;
    private Circuit circuit;

    public TotalResponseTimeWidget(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public void updateDataModel(GraphStage mechanismStage) {
        totalResponseTimeLabel.setText(String.valueOf(circuit.totalResponseTime()));
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
