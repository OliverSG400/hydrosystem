package edu.khai.k105.hydrosystem.gui.project.editor;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.element.AccumulatorElement;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.element.MechanismElement;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.element.PipelineElement;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.element.ResistanceElement;
import edu.khai.k105.hydrosystem.dataModel.project.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CircuitElementsEditor {

    private JPanel contentPane;
    private JButton createPipelineButton;
    private JButton createResistanceButton;
    private JButton createMechanismButton;
    private JButton createAccumulatorButton;

    public CircuitElementsEditor(final JTree projectTree, final Project currentProject) {
        createPipelineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProject.getCircuit().getElements().add(new PipelineElement());
                projectTree.setModel(new ProjectTreeModel(currentProject));
                expandAll(projectTree);
            }
        });
        createResistanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProject.getCircuit().getElements().add(new ResistanceElement());
                projectTree.setModel(new ProjectTreeModel(currentProject));
                expandAll(projectTree);
            }
        });
        createAccumulatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProject.getCircuit().getElements().add(new AccumulatorElement());
                projectTree.setModel(new ProjectTreeModel(currentProject));
                expandAll(projectTree);
            }
        });
        createMechanismButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProject.getCircuit().getElements().add(new MechanismElement());
                projectTree.setModel(new ProjectTreeModel(currentProject));
                expandAll(projectTree);
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void expandAll(JTree tree) {
        int row = 0;
        while (row < tree.getRowCount()) {
            tree.expandRow(row);
            row++;
        }
    }

}
