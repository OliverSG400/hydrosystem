package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.graph.GraphStage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Pager {
    private JButton previousButton;
    private JComboBox sectionComboBox;
    private JButton nextButton;
    private JPanel contentPane;
    private List<GraphStage> stageGraph;
    private Viewer recalculator;

    public Pager(List<GraphStage> stageGraph, final Viewer recalculator) {
        this.stageGraph = stageGraph;
        this.recalculator = recalculator;
        sectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculator.recalculate((GraphStage) sectionComboBox.getSelectedItem());
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sectionComboBox.getSelectedIndex() < sectionComboBox.getItemCount() - 1) {
                    sectionComboBox.setSelectedIndex(sectionComboBox.getSelectedIndex() + 1);
                    recalculator.recalculate((GraphStage) sectionComboBox.getSelectedItem());
                }
            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sectionComboBox.getSelectedIndex() > 0) {
                    sectionComboBox.setSelectedIndex(sectionComboBox.getSelectedIndex() - 1);
                    recalculator.recalculate((GraphStage) sectionComboBox.getSelectedItem());
                }
            }
        });
    }

    public GraphStage getCurrentStageGraph() {
        return (GraphStage) sectionComboBox.getSelectedItem();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        sectionComboBox = new JComboBox(stageGraph.toArray(new GraphStage[0]));
        sectionComboBox.setSelectedIndex(0);
    }
}
