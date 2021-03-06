package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;

import javax.swing.*;
import java.text.NumberFormat;

public class TotalResponseTimeWidget implements UpdateAble {

    private JLabel totalResponseTimeLabel;
    private JPanel contentPane;
    private Circuit circuit;

    public TotalResponseTimeWidget(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public void updateDataModel(GraphStage mechanismStage) {
        double responseTime;
        if (circuit.getAccumulator() != null) {
            try {
                responseTime = circuit.responseTimeConsiderAccumulator();
                totalResponseTimeLabel.setText(NumberFormat.getNumberInstance().format(responseTime));
            } catch (Exception e) {
                totalResponseTimeLabel.setText(e.getMessage());
            }
        } else {
            responseTime = circuit.responseTime();
            totalResponseTimeLabel.setText(NumberFormat.getNumberInstance().format(responseTime));
        }
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
