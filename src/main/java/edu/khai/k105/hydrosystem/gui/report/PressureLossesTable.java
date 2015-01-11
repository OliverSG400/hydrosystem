package edu.khai.k105.hydrosystem.gui.report;

import edu.khai.k105.hydrosystem.dataModel.Application;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.VerticalAlignment;

import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

public class PressureLossesTable {

    public static void build(Application application, JasperReportBuilder reportBuilder) {
        StyleBuilder titleStyle = stl.style()
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(15);
        StyleBuilder title2Style = stl.style()
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(10);
//        reportBuilder.columns();
//        reportBuilder.setDataSource(new JRAbstractBeanDataSource() {
//            @Override
//            public void moveFirst() throws JRException {
//
//            }
//
//            @Override
//            public boolean next() throws JRException {
//                return false;
//            }
//
//            @Override
//            public Object getFieldValue(JRField jrField) throws JRException {
//                return null;
//            }
//        });
    }

}
