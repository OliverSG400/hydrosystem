package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;

import javax.swing.*;
import java.text.NumberFormat;

public class StageResponseTimeWidget implements UpdateAble {

    private JPanel contentPane;
    private JLabel stageTimeLabel;
    private Circuit circuit;

    public StageResponseTimeWidget(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public void updateDataModel(GraphStage mechanismStage) {
        double responseTime;
        if (circuit.getAccumulator() != null) {
            try {
                responseTime = circuit.responseTimeConsiderAccumulator(mechanismStage);
                stageTimeLabel.setText(NumberFormat.getNumberInstance().format(responseTime));
            } catch (Exception e) {
                stageTimeLabel.setText(e.getMessage());
            }
        } else {
            responseTime = circuit.responseTime(circuit.operatingPoint(mechanismStage).x, mechanismStage);
            stageTimeLabel.setText(NumberFormat.getNumberInstance().format(responseTime));
        }
//        stageTimeLabel.setText(String.format("%.16f", circuit.responseTime(circuit.operatingPoint(mechanismStage).x, mechanismStage)));
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
