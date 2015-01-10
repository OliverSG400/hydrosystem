package edu.khai.k105.hydrosystem.gui.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.element.MechanismElement;
import edu.khai.k105.hydrosystem.gui.graph.GraphEditor;
import edu.khai.k105.hydrosystem.application.project.graph.GraphModel;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;
import edu.khai.k105.hydrosystem.gui.project.circuit.HydraulicEditor;
import edu.khai.k105.hydrosystem.gui.project.circuit.ProjectTreeModel;
import edu.khai.k105.hydrosystem.application.project.Project;
import edu.khai.k105.hydrosystem.gui.validation.NoCharacterVerifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MechanismEditor {
    private JPanel contentPane;
    private JFormattedTextField pistonSquareTextField;
    private JButton varLoadCharButton;
    private JButton removeElementButton;
    private MechanismElement mechanismElement;

    public MechanismEditor(final MechanismElement mechanismElement, final Project currentProject, final HydraulicEditor hydraulicEditor) {
        this.mechanismElement = mechanismElement;
        pistonSquareTextField.setText(String.valueOf(mechanismElement.getPistonSquare()));
        pistonSquareTextField.setInputVerifier(NoCharacterVerifier.getInstance());
        pistonSquareTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    float value = Float.parseFloat(pistonSquareTextField.getText());
                    mechanismElement.setPistonSquare(value);
                } catch (NumberFormatException ex) {
                    System.out.println("Number format exception");
                }
            }
        });
        varLoadCharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGraphEditor(mechanismElement.getVariableLoadCharacteristic(), "Внешняя нагрузка");
            }
        });
        removeElementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProject.getCircuit().getElements().remove(mechanismElement);
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

    private void createGraphEditor(GraphSeries series, String title) {
        JFrame f = new JFrame(title);
        GraphModel graphModel = new GraphModel();
        graphModel.getSeries().add(series);
        GraphEditor graphEditor = new GraphEditor(graphModel);
        f.setContentPane(graphEditor.getContentPane());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JFormattedTextField getPistonSquareTextField() {
        return pistonSquareTextField;
    }

    public void setPistonSquareTextField(JFormattedTextField pistonSquareTextField) {
        this.pistonSquareTextField = pistonSquareTextField;
    }

    public JButton getVarLoadCharButton() {
        return varLoadCharButton;
    }

    public void setVarLoadCharButton(JButton varLoadCharButton) {
        this.varLoadCharButton = varLoadCharButton;
    }
}
