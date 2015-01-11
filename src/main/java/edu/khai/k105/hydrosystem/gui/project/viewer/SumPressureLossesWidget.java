package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphPoint;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;

import javax.swing.*;
import java.util.List;

public class SumPressureLossesWidget implements UpdateAble {
    private JPanel contentPane;
    private JTable sumLossesTable;
    private Circuit circuit;

    public SumPressureLossesWidget(Circuit circuit) {
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
    public void updateDataModel(GraphStage mechanismStage) {
        sumLossesTable.setModel(new PressureLossesTableModel(getSumLossesData(circuit, mechanismStage), getColumns(circuit)));
    }

}
