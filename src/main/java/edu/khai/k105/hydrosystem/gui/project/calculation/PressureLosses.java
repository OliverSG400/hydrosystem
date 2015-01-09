package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.project.graph.GraphStage;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Arrays;
import java.util.List;


public class PressureLosses implements Recalculator {
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JTable sumLossesTable;
    private JTable localLossesTable;
    private Pager pager;
    private Circuit circuit;

    public PressureLosses(Circuit circuit) {
        this.circuit = circuit;
        recalculate(pager.getCurrentStageGraph());
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

    private Double[][] getLocalLossesData(Circuit circuit, GraphStage mechanismStage) {
        return circuit.getLocalLossesData(mechanismStage);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    @Override
    public void recalculate(GraphStage mechanismStage) {
//        sumLossesTable = new JTable(new PressureLossesTableModel(getSumLossesData(circuit, mechanismStage), getColumns(circuit)));
//        localLossesTable = new JTable(new PressureLossesTableModel(getLocalLossesData(circuit, mechanismStage), getColumns(circuit)));
        sumLossesTable.setModel(new PressureLossesTableModel(getSumLossesData(circuit, mechanismStage), getColumns(circuit)));
        localLossesTable.setModel(new PressureLossesTableModel(getLocalLossesData(circuit, mechanismStage), getColumns(circuit)));
    }

    private void createUIComponents() {
        pager = new Pager(circuit.getMechanism().getStageGraph(), this);
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
