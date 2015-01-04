package edu.khai.k105.hydrosystem.gui.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.element.PipelineElement;
import edu.khai.k105.hydrosystem.gui.project.circuit.HydraulicEditor;
import edu.khai.k105.hydrosystem.gui.project.circuit.ProjectTreeModel;
import edu.khai.k105.hydrosystem.application.project.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Created by Oliver on 03.01.2015.
 */
public class PipelineEditor {
    private JPanel contentPane;
    private JFormattedTextField lengthTextField;
    private JFormattedTextField diameterTextField;
    private JButton removeElementButton;
    private PipelineElement pipelineElement;

    public PipelineEditor(final PipelineElement pipelineElement, final Project currentProject, final HydraulicEditor hydraulicEditor) {
        this.pipelineElement = pipelineElement;
        lengthTextField.setText(String.valueOf(pipelineElement.getLength()));
        diameterTextField.setText(String.valueOf(pipelineElement.getDiameter()));
        lengthTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(lengthTextField.getText());
                    pipelineElement.setLength(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        diameterTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(diameterTextField.getText());
                    pipelineElement.setDiameter(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        removeElementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProject.getCircuit().getElements().remove(pipelineElement);
                hydraulicEditor.getProjectTree().setModel(new ProjectTreeModel(currentProject));
                expandAll(hydraulicEditor.getProjectTree());
                //hydraulicEditor.getContentPane().removeAll();
            }
        });
    }

    private void expandAll(JTree tree) {
        int row = 0;
        while (row < tree.getRowCount()) {
            tree.expandRow(row);
            row++;
        }
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JFormattedTextField getLengthTextField() {
        return lengthTextField;
    }

    public void setLengthTextField(JFormattedTextField lengthTextField) {
        this.lengthTextField = lengthTextField;
    }

    public JFormattedTextField getDiameterTextField() {
        return diameterTextField;
    }

    public void setDiameterTextField(JFormattedTextField diameterTextField) {
        this.diameterTextField = diameterTextField;
    }
}
