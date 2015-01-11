package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.graph.GraphStage;

import javax.swing.*;
import java.util.List;


public class LocalPressureLosses implements Viewer {
    private JPanel contentPane;
    private JTable localLossesTable;
    private Circuit circuit;

    public LocalPressureLosses(Circuit circuit) {
        this.circuit = circuit;
    }

    private String[] getColumns(Circuit circuit) {
        List<GraphPoint> pumpPoints = circuit.getPump().getPumpCharacteristic().getPoints();
        String[] columns = new String[pumpPoints.size()];
        for (int q = 0; q < pumpPoints.size(); q++) {
            columns[q] = String.valueOf(pumpPoints.get(q).x);
        }
        return columns;
    }

    private Double[][] getLocalLossesData(Circuit circuit, GraphStage mechanismStage) {
        return circuit.getLocalLossesData(mechanismStage);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    @Override
    public void recalculate(GraphStage mechanismStage) {
        localLossesTable.setModel(new PressureLossesTableModel(getLocalLossesData(circuit, mechanismStage), getColumns(circuit)));
    }

}
