package edu.khai.k105.hydrosystem.gui.project.editor.element;

import edu.khai.k105.hydrosystem.dataModel.project.Project;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.element.AccumulatorElement;
import edu.khai.k105.hydrosystem.gui.project.editor.SchemeEditor;
import edu.khai.k105.hydrosystem.gui.project.editor.ProjectTreeModel;
import edu.khai.k105.hydrosystem.gui.validation.NoCharacterVerifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AccumulatorEditor {
    private JPanel contentPane;
    private JFormattedTextField maxPressureTextField;
    private JFormattedTextField minVolumeTextField;
    private JFormattedTextField minPressureTextField;
    private JFormattedTextField maxVolumeTextField;
    private JFormattedTextField politropaTextField;
    private JButton removeElementButton;

    public AccumulatorEditor(final AccumulatorElement accumulatorElement, final Project currentProject, final SchemeEditor schemeEditor) {
        maxPressureTextField.setText(String.valueOf(accumulatorElement.getMaxPressure()));
        minVolumeTextField.setText(String.valueOf(accumulatorElement.getMinVolume()));
        minPressureTextField.setText(String.valueOf(accumulatorElement.getMinPressure()));
        maxVolumeTextField.setText(String.valueOf(accumulatorElement.getMaxVolume()));
        politropaTextField.setText(String.valueOf(accumulatorElement.getPolitropa()));
        maxPressureTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        minVolumeTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        minPressureTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        maxVolumeTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        politropaTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        maxPressureTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(maxPressureTextField.getText());
                    accumulatorElement.setMaxPressure(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        minVolumeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(minVolumeTextField.getText());
                    accumulatorElement.setMinVolume(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        minPressureTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(minPressureTextField.getText());
                    accumulatorElement.setMinPressure(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        maxVolumeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(maxVolumeTextField.getText());
                    accumulatorElement.setMaxVolume(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        politropaTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(politropaTextField.getText());
                    accumulatorElement.setPolitropa(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        removeElementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProject.getCircuit().getElements().remove(accumulatorElement);
                schemeEditor.getProjectTree().setModel(new ProjectTreeModel(currentProject));
                expandAll(schemeEditor.getProjectTree());
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
}
