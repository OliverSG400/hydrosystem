package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.graph.GraphStage;
import edu.khai.k105.hydrosystem.gui.graph.JGraph;

import javax.swing.*;

public class CalculationViewer implements Viewer {

    private JPanel contentPane;
    private JTabbedPane viewTabbedPane;
    private Pager pager;
    private JLabel operatingPointLabel;
    private JPanel PropertiesPanel;
    private LocalPressureLosses localPressureLossesViewer;
    private SumPressureLosses sumPressureLossesViewer;
    private OperatingPointViewer operatingPointViewer;
    private Circuit circuit;

    public CalculationViewer(Circuit circuit) {
        this.circuit = circuit;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        localPressureLossesViewer = new LocalPressureLosses(circuit);
        sumPressureLossesViewer = new SumPressureLosses(circuit);
        operatingPointLabel = new JLabel("0");
        operatingPointViewer = new OperatingPointViewer(circuit, operatingPointLabel);

        pager = new Pager(circuit.getMechanism().getStageGraph(), this);
        this.recalculate(pager.getCurrentStageGraph());
    }

    @Override
    public void recalculate(GraphStage mechanismStage) {
        localPressureLossesViewer.recalculate(mechanismStage);
        sumPressureLossesViewer.recalculate(mechanismStage);
        operatingPointViewer.recalculate(mechanismStage);
    }
}
