package edu.khai.k105.hydrosystem.gui.report;

import edu.khai.k105.hydrosystem.application.Application;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class Reporter {

    public void createReport(Application application) {
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
            TitleAndFooter.build(application, "заголовок страницы", reportBuilder);
            PressureLossesTable.build(application, reportBuilder);
            reportBuilder.summary(itemChart);
            reportBuilder.show();
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

}
