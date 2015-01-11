package edu.khai.k105.hydrosystem.gui.project.editor;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.gui.validation.NoCharacterVerifier;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Created by Oliver on 03.01.2015.
 */
public class FluidEditor {
    private JPanel contentPane;
    private JFormattedTextField kinematicalViscosityTextField;
    private JFormattedTextField specificWeightTextField;
    private Fluid fluid;

    public FluidEditor(final Fluid fluid) {
        this.fluid = fluid;
        kinematicalViscosityTextField.setText(String.valueOf(fluid.getKinematicalViscosity()));
        specificWeightTextField.setText(String.valueOf(fluid.getSpecificWeight()));
        kinematicalViscosityTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        specificWeightTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        kinematicalViscosityTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(kinematicalViscosityTextField.getText());
                    fluid.setKinematicalViscosity(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        specificWeightTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(specificWeightTextField.getText());
                    fluid.setSpecificWeight(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
