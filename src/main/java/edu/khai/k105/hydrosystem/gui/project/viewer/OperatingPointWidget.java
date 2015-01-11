package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.graph.GraphPoint;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;

import javax.swing.*;

public class OperatingPointWidget implements UpdateAble {

    private JPanel contentPane;
    private JLabel operatingPointLabel;
    private Circuit circuit;

    public OperatingPointWidget(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public void updateDataModel(GraphStage mechanismStage) {
        GraphPoint operatingPoint = circuit.operatingPoint(mechanismStage);
        if (operatingPoint != null) {
            operatingPointLabel.setText(operatingPoint.toString());
        } else {
            operatingPointLabel.setText("отсутствует");
        }
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
