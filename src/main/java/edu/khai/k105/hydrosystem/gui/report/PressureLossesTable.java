package edu.khai.k105.hydrosystem.gui.report;

import edu.khai.k105.hydrosystem.dataModel.Application;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphPoint;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;

import java.awt.*;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class PressureLossesTable {

    public static void build(Circuit circuit, JasperReportBuilder reportBuilder) {
        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);

        reportBuilder.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);

        GraphStage currentStage = circuit.getMechanism().getStageGraph().get(0);
        Double[][] pressureLosses = circuit
                .getSumLossesData(currentStage);
        int columnCount = circuit.getPump().getPumpCharacteristic().getPoints().size() + 1;
        TextColumnBuilder<?>[] columns = new TextColumnBuilder[columnCount];
        columns[0] = col.columnRowNumberColumn("№")
//                .setFixedColumns(2)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        for (int i = 1; i < columnCount; i++) {
            columns[i] = col.column(String.valueOf(circuit.getPump().getPumpCharacteristic().getPoints().get(i - 1).x)
                     , String.valueOf(i - 1), type.doubleType());
        }
        reportBuilder.columns(columns);
        reportBuilder.setColumnTitleStyle(columnTitleStyle)
                .highlightDetailEvenRows();

        reportBuilder.setDataSource(new PressureLossesDataSource(pressureLosses));
    }

    private static class PressureLossesDataSource extends JRAbstractBeanDataSource {

        private Double[][] pressureLosses;
        private int row = -1;

        public PressureLossesDataSource(Double[][] pressureLosses) {
            super(false);
            this.pressureLosses = pressureLosses;
        }

        @Override
        public void moveFirst() throws JRException {

        }

        @Override
        public boolean next() throws JRException {
            if (row < pressureLosses.length - 1) {
                row++;
                return true;
            }
            return false;
        }

        @Override
        public Object getFieldValue(JRField jrField) throws JRException {
            int columnIndex = Integer.parseInt(jrField.getName());
            return pressureLosses[row][columnIndex];
        }

    }

}
