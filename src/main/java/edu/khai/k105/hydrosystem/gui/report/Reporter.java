package edu.khai.k105.hydrosystem.gui.report;

import edu.khai.k105.hydrosystem.dataModel.Application;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Locale;

public class Reporter {

    public void createReport(Circuit circuit, GraphStage currentMechanismStage) {
        try {
            Bar3DChartBuilder itemChart = DynamicReports.cht.bar3DChart()
                    .setTitle("<a href=\"http://www.dynamicreports.org/examples/sales\" title=\"Sales\">Sales</a> by item");
//                    .setCategory(itemColumn)
//                    .addSerie(
//                            DynamicReports.cht.serie(unitPriceColumn), DynamicReports.cht.serie(priceColumn));

//            Bar3DChartBuilder itemChart2 = cht.bar3DChart()
//
//                    .setTitle("<a href="http://www.dynamicreports.org/examples/sales" title="Sales">Sales</a> by item")
//
//                    .setCategory(itemColumn)
//
//                    .setUseSeriesAsCategory(true)
//
//                    .addSerie(
//
//                            cht.serie(unitPriceColumn), cht.serie(priceColumn));
            JasperReportBuilder reportBuilder = DynamicReports.report();
            reportBuilder.setPageMargin(DynamicReports.margin().setLeft(50).setTop(20).setRight(20).setBottom(20));
            TitleAndFooter.build("Суммарные потери давления", currentMechanismStage.toString(), reportBuilder);
            PressureLossesTable.build(circuit, reportBuilder);
            //reportBuilder.summary(itemChart);
//            reportBuilder.show(false);
            JasperViewer jasperViewer = new JasperViewer(reportBuilder.toJasperPrint(), false);
//            jasperViewer.setIconImage(...);
            jasperViewer.setTitle("Отчет");
            jasperViewer.setVisible(true);
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

}
