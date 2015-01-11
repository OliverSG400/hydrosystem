package edu.khai.k105.hydrosystem.gui.report;

import edu.khai.k105.hydrosystem.application.Application;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class TitleAndFooter {

    public static void build(Application application, String title, JasperReportBuilder reportBuilder) {
        StyleBuilder titleStyle = stl.style()
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(15);
        StyleBuilder title2Style = stl.style()
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(10);
        reportBuilder.title(
                cmp.horizontalList().add(
//                        cmp.image(getClass().getResourceAsStream("../images/dynamicreports.png")).setFixedDimension(80, 80),
                        cmp.text(title).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
                        cmp.text(application.getCurrentProject().getTitle()).setStyle(title2Style).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                        .newRow(10).add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)));
        reportBuilder.pageFooter(cmp.pageXofY());
    }

}
