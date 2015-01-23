package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.gui.report.Reporter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalculationPanel {

    private JPanel contentPane;
    private JTabbedPane viewTabbedPane;
    private Pager pager;
    private JPanel PropertiesPanel;
    private LocalPressureLossesWidget localPressureLossesViewer;
    private SumPressureLossesWidget sumPressureLossesViewer;
    private OperatingPointGraphWidget operatingPointGraphViewer;
    private AccumulatorGraphWidget accumulatorGraphViewer;
    private OperatingPointWidget operatingPointWidget;
    private TotalResponseTimeWidget totalResponseTimeWidget;
    private ReporterWidget reporterWidget;
    private Circuit circuit;

    public CalculationPanel(final Circuit circuit) {
        this.circuit = circuit;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        localPressureLossesViewer = new LocalPressureLossesWidget(circuit);
        sumPressureLossesViewer = new SumPressureLossesWidget(circuit);
        operatingPointWidget = new OperatingPointWidget(circuit);
        operatingPointGraphViewer = new OperatingPointGraphWidget(circuit);
        accumulatorGraphViewer = new AccumulatorGraphWidget(circuit);
//        stageResponseTimeWidget = new StageResponseTimeWidget(circuit);
        totalResponseTimeWidget = new TotalResponseTimeWidget(circuit);
        reporterWidget = new ReporterWidget(circuit);
        pager = new Pager(circuit.getMechanism().getStageGraph(), new ArrayList<UpdateAble>());
        pager.addUpdateAbleListener(localPressureLossesViewer);
        pager.addUpdateAbleListener(sumPressureLossesViewer);
        pager.addUpdateAbleListener(operatingPointWidget);
        pager.addUpdateAbleListener(operatingPointGraphViewer);
        pager.addUpdateAbleListener(accumulatorGraphViewer);
//        pager.addUpdateAbleListener(stageResponseTimeWidget);
        pager.addUpdateAbleListener(totalResponseTimeWidget);
        pager.addUpdateAbleListener(reporterWidget);
        pager.invokeListenersUpdate();
    }

}
