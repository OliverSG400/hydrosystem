package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphPoint;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.Enumeration;
import java.util.List;


public class LocalPressureLossesWidget implements UpdateAble {
    private JPanel contentPane;
    private JTable localLossesTable;
    private Circuit circuit;

    public LocalPressureLossesWidget(Circuit circuit) {
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
    public void updateDataModel(GraphStage mechanismStage) {
        localLossesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        localLossesTable.setModel(new PressureLossesTableModel(getLocalLossesData(circuit, mechanismStage), getColumns(circuit)));
        Enumeration<TableColumn> columns = localLossesTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            columns.nextElement().setPreferredWidth(150);
        }
    }

}
