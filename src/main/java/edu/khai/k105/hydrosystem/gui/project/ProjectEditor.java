package edu.khai.k105.hydrosystem.gui.project;

import edu.khai.k105.hydrosystem.application.project.Project;

import javax.swing.*;
import java.awt.event.*;

public class ProjectEditor {

    private JPanel contentPane;
    private JTextField titleTextField;
    private JTextArea descriptionTextArea;
    private Project project;

    public ProjectEditor(final Project project) {
        this.project = project;
        titleTextField.setText(project.getTitle());
        descriptionTextArea.setText(project.getDescription());
        titleTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                project.setTitle(titleTextField.getText());
            }
        });
        descriptionTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                project.setDescription(descriptionTextArea.getText());
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

}
