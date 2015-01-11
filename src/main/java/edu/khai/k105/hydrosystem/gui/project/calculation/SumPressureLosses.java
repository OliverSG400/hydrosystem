package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.graph.GraphStage;

import javax.swing.*;
import java.util.List;

public class SumPressureLosses implements Viewer {
    private JPanel contentPane;
    private JTable sumLossesTable;
    private Circuit circuit;

    public SumPressureLosses(Circuit circuit) {
        this.circuit = circuit;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private String[] getColumns(Circuit circuit) {
        List<GraphPoint> pumpPoints = circuit.getPump().getPumpCharacteristic().getPoints();
        String[] columns = new String[pumpPoints.size()];
        for (int q = 0; q < pumpPoints.size(); q++) {
            columns[q] = String.valueOf(pumpPoints.get(q).x);
        }
        return columns;
    }

    private Double[][] getSumLossesData(Circuit circuit, GraphStage mechanismStage) {
        return circuit.getSumLossesData(mechanismStage);
    }

    @Override
    public void recalculate(GraphStage mechanismStage) {
        sumLossesTable.setModel(new PressureLossesTableModel(getSumLossesData(circuit, mechanismStage), getColumns(circuit)));
    }

}
