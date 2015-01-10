package edu.khai.k105.hydrosystem.gui.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.element.ResistanceElement;
import edu.khai.k105.hydrosystem.gui.project.circuit.HydraulicEditor;
import edu.khai.k105.hydrosystem.gui.project.circuit.ProjectTreeModel;
import edu.khai.k105.hydrosystem.application.project.Project;
import edu.khai.k105.hydrosystem.gui.validation.NoCharacterVerifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ResistanceEditor {
    private JPanel contentPane;
    private JComboBox typeComboBox;
    private JFormattedTextField coefficientTextField;
    private JFormattedTextField inputDiameterTextField;
    private JButton removeElementButton;
    private ResistanceElement resistanceElement;

    public ResistanceEditor(final ResistanceElement resistanceElement, final Project currentProject, final HydraulicEditor hydraulicEditor) {
        this.resistanceElement = resistanceElement;
        coefficientTextField.setText(String.valueOf(resistanceElement.getCoefficient()));
        inputDiameterTextField.setText(String.valueOf(resistanceElement.getInputDiameter()));
        typeComboBox.setSelectedItem(resistanceElement.getType());
        coefficientTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        inputDiameterTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        coefficientTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(coefficientTextField.getText());
                    resistanceElement.setCoefficient(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        inputDiameterTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(inputDiameterTextField.getText());
                    resistanceElement.setInputDiameter(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resistanceElement.setType(typeComboBox.getSelectedItem().toString());
            }
        });
        removeElementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProject.getCircuit().getElements().remove(resistanceElement);
                hydraulicEditor.getProjectTree().setModel(new ProjectTreeModel(currentProject));
                expandAll(hydraulicEditor.getProjectTree());
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

    public JComboBox getTypeComboBox() {
        return typeComboBox;
    }

    public void setTypeComboBox(JComboBox typeComboBox) {
        this.typeComboBox = typeComboBox;
    }

    public JFormattedTextField getCoefficientTextField() {
        return coefficientTextField;
    }

    public void setCoefficientTextField(JFormattedTextField coefficientTextField) {
        this.coefficientTextField = coefficientTextField;
    }

    public JFormattedTextField getInputDiameterTextField() {
        return inputDiameterTextField;
    }

    public void setInputDiameterTextField(JFormattedTextField inputDiameterTextField) {
        this.inputDiameterTextField = inputDiameterTextField;
    }

    private void createUIComponents() {
        typeComboBox = new JComboBox(new String[] {
                "Вход в бак из трубопровода"
                , "Выход из бака в трубопровод"
                , "Колено, закругленное под углом в 90 градусов"
                , "Кран запорный"
                , "Обратный клапан"
                , "Расходомер"
                , "Соединение дюритовое"
                , "Соединение самозапорное"
                , "Тройник"
                , "Угольник"
                , "Фильтр сетчатки"});
    }
}
