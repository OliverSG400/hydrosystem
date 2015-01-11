package edu.khai.k105.hydrosystem.gui.project.editor;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.gui.validation.NoCharacterVerifier;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Created by Oliver on 04.01.2015.
 */
public class CircuitEditor {
    private JPanel contentPane;
    private JFormattedTextField gravityAccelerationTextField;
    private Circuit circuit;

    public CircuitEditor(final Circuit circuit) {
        this.circuit = circuit;
        gravityAccelerationTextField.setText(String.valueOf(circuit.getGravityAcceleration()));
        gravityAccelerationTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        gravityAccelerationTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(gravityAccelerationTextField.getText());
                    circuit.setGravityAcceleration(value);
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
