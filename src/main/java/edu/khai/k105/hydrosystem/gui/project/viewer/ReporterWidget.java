package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.gui.report.Reporter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReporterWidget implements UpdateAble {

    private JButton generateReportButton;
    private JPanel contentPane;
    private GraphStage currentMechanismStage;

    public ReporterWidget(final Circuit circuit) {
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMechanismStage != null) {
                    new Reporter().createReport(circuit, currentMechanismStage);
                }
            }
        });
    }

    @Override
    public void updateDataModel(GraphStage mechanismStage) {
        currentMechanismStage = mechanismStage;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
