package edu.khai.k105.hydrosystem.gui.project.calculation;

import javax.swing.table.AbstractTableModel;

public class PressureLossesTableModel extends AbstractTableModel {

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
