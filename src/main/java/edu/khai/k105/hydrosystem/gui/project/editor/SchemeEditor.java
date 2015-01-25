package edu.khai.k105.hydrosystem.gui.project.editor;

import edu.khai.k105.hydrosystem.dataModel.Application;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Pump;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.element.*;
import edu.khai.k105.hydrosystem.gui.project.editor.element.AccumulatorEditor;
import edu.khai.k105.hydrosystem.gui.project.editor.element.MechanismEditor;
import edu.khai.k105.hydrosystem.gui.project.editor.element.PipelineEditor;
import edu.khai.k105.hydrosystem.gui.project.editor.element.ResistanceEditor;
import edu.khai.k105.hydrosystem.dataModel.project.Project;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class SchemeEditor {

    private JPanel contentPane;
    private JTree projectTree;
    private JPanel propertiesPanel;
    private JButton upButton;
    private JButton downButton;
    private Application application;

    public JPanel getContentPane() {
        return contentPane;
    }

    public SchemeEditor(final Application application) {
        this.application = application;
        projectTree.setModel(new ProjectTreeModel(application.getCurrentProject()));
        projectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        projectTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                showRelatedPanel(projectTree.getLastSelectedPathComponent());
            }
        });
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selected = projectTree.getLastSelectedPathComponent();
                List<Element> elements = application.getCurrentProject().getCircuit().getElements();
                int i = elements.indexOf(selected);
                if (i > 0) {
                    Collections.swap(elements, i, i - 1);
                }
                getProjectTree().setModel(new ProjectTreeModel(application.getCurrentProject()));
                expandAll(getProjectTree());
            }
        });
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selected = projectTree.getLastSelectedPathComponent();
                List<Element> elements = application.getCurrentProject().getCircuit().getElements();
                int i = elements.indexOf(selected);
                if (i < elements.size() - 1) {
                    Collections.swap(elements, i, i + 1);
                }
                getProjectTree().setModel(new ProjectTreeModel(application.getCurrentProject()));
                expandAll(getProjectTree());
            }
        });
        expandAll(projectTree);
    }

    private void showRelatedPanel(Object selection) {
        propertiesPanel.removeAll();
        if (selection instanceof Project) {
            ProjectEditor projectEditor = new ProjectEditor((Project) selection);
            propertiesPanel.add(projectEditor.getContentPane(), BorderLayout.CENTER);
        }
        if (selection instanceof Circuit) {
            CircuitEditor circuitEditor = new CircuitEditor((Circuit) selection);
            propertiesPanel.add(circuitEditor.getContentPane(), BorderLayout.CENTER);
        }
        if (selection instanceof Fluid) {
            FluidEditor fluidEditor = new FluidEditor((Fluid) selection);
            propertiesPanel.add(fluidEditor.getContentPane(), BorderLayout.CENTER);
        }
        if (selection instanceof Pump) {
            PumpEditor pumpEditor = new PumpEditor((Pump) selection);
            propertiesPanel.add(pumpEditor.getContentPane(), BorderLayout.CENTER);
        }
        if (selection instanceof List) {
            CircuitElementsEditor circuitElementsEditor = new CircuitElementsEditor(projectTree, application.getCurrentProject());
            propertiesPanel.add(circuitElementsEditor.getContentPane(), BorderLayout.CENTER);
        }
        if (selection instanceof PipelineElement) {
            PipelineEditor pipelineEditor = new PipelineEditor((PipelineElement) selection, application.getCurrentProject(), this);
            propertiesPanel.add(pipelineEditor.getContentPane(), BorderLayout.CENTER);
        }
        if (selection instanceof ResistanceElement) {
            ResistanceEditor resistanceEditor = new ResistanceEditor((ResistanceElement) selection, application.getCurrentProject(), this);
            propertiesPanel.add(resistanceEditor.getContentPane(), BorderLayout.CENTER);
        }
        if (selection instanceof MechanismElement) {
            MechanismEditor mechanismEditor = new MechanismEditor((MechanismElement) selection, application.getCurrentProject(), this);
            propertiesPanel.add(mechanismEditor.getContentPane(), BorderLayout.CENTER);
        }
        if (selection instanceof AccumulatorElement) {
            AccumulatorEditor accumulatorEditor = new AccumulatorEditor((AccumulatorElement) selection, application.getCurrentProject(), this);
            propertiesPanel.add(accumulatorEditor.getContentPane(), BorderLayout.CENTER);
        }
        propertiesPanel.validate();
    }

    private void expandAll(JTree tree) {
        int row = 0;
        while (row < tree.getRowCount()) {
            tree.expandRow(row);
            row++;
        }
    }

    public JPanel getPropertiesPanel() {
        return propertiesPanel;
    }

    public JTree getProjectTree() {
        return projectTree;
    }
}
