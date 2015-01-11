package edu.khai.k105.hydrosystem.gui.project.viewer;

import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Pager {

    private List<UpdateAble> widgets;
    private JButton previousButton;
    private JComboBox sectionComboBox;
    private JButton nextButton;
    private JPanel contentPane;
    private List<GraphStage> stageGraph;

    public Pager(List<GraphStage> stageGraph, List<UpdateAble> widgets) {
        this.stageGraph = stageGraph;
        this.widgets = widgets;
        sectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invokeListenersUpdate();
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sectionComboBox.getSelectedIndex() < sectionComboBox.getItemCount() - 1) {
                    sectionComboBox.setSelectedIndex(sectionComboBox.getSelectedIndex() + 1);
                    invokeListenersUpdate();
                }
            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sectionComboBox.getSelectedIndex() > 0) {
                    sectionComboBox.setSelectedIndex(sectionComboBox.getSelectedIndex() - 1);
                    invokeListenersUpdate();
                }
            }
        });
    }

    public void addUpdateAbleListener(UpdateAble widget) {
        widgets.add(widget);
    }

    public void invokeListenersUpdate() {
        for (UpdateAble widget : widgets) {
            widget.updateDataModel((GraphStage) sectionComboBox.getSelectedItem());
        }
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        sectionComboBox = new JComboBox(stageGraph.toArray(new GraphStage[0]));
        sectionComboBox.setSelectedIndex(0);
    }

}
