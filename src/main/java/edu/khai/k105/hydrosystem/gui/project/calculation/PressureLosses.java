package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Arrays;
import java.util.List;


public class PressureLosses {
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JTable sumLossesTable;
    private JTable localLossesTable;
    private Circuit circuit;

    public PressureLosses(Circuit circuit) {
        this.circuit = circuit;
        sumLossesTable.setModel(new PressureLossesTableModel(getSumLossesData(circuit), getColumns(circuit)));
        localLossesTable.setModel(new PressureLossesTableModel(getLocalLossesData(circuit), getColumns(circuit)));
    }

    private String[] getColumns(Circuit circuit) {
        List<GraphPoint> pumpPoints = circuit.getPump().getPumpCharacteristic().getPoints();
        String[] columns = new String[pumpPoints.size()];
        for (int q = 0; q < pumpPoints.size(); q++) {
            columns[q] = String.valueOf(pumpPoints.get(q).x);
        }
        return columns;
    }

    private Double[][] getSumLossesData(Circuit circuit) {
        return circuit.getSumLossesData();
    }

    private Double[][] getLocalLossesData(Circuit circuit) {
        return circuit.getLocalLossesData();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private class PressureLossesTableModel extends AbstractTableModel {

        String[] columns;
        private Double[][] data;

        public PressureLossesTableModel(Double[][] data, String[] columns) {
            this.data = data;
            this.columns = columns;
        }

        @Override
        public String getColumnName(int index) {
            return columns[index];
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return data[0].length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
    }

}
