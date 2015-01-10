package edu.khai.k105.hydrosystem.gui.project.circuit;

import edu.khai.k105.hydrosystem.application.Application;
import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.application.project.circuit.Pump;
import edu.khai.k105.hydrosystem.application.project.circuit.element.AccumulatorElement;
import edu.khai.k105.hydrosystem.application.project.circuit.element.MechanismElement;
import edu.khai.k105.hydrosystem.application.project.circuit.element.PipelineElement;
import edu.khai.k105.hydrosystem.application.project.circuit.element.ResistanceElement;
import edu.khai.k105.hydrosystem.gui.project.circuit.element.AccumulatorEditor;
import edu.khai.k105.hydrosystem.gui.project.circuit.element.MechanismEditor;
import edu.khai.k105.hydrosystem.gui.project.circuit.element.PipelineEditor;
import edu.khai.k105.hydrosystem.gui.project.circuit.element.ResistanceEditor;
import edu.khai.k105.hydrosystem.application.project.Project;
import edu.khai.k105.hydrosystem.gui.project.ProjectEditor;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.List;

public class SchemeEditor {

    private JPanel contentPane;
    private JTree projectTree;
    private JPanel propertiesPanel;
    private Application application;

    public JPanel getContentPane() {
        return contentPane;
    }

    public SchemeEditor(Application application) {
        this.application = application;
        projectTree.setModel(new ProjectTreeModel(application.getCurrentProject()));
        projectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        projectTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                showRelatedPanel(projectTree.getLastSelectedPathComponent());
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
