package edu.khai.k105.hydrosystem;

import edu.khai.k105.hydrosystem.project.ProjectWindow;

import javax.swing.*;

public class Start {

    public static void main(String[] args) {
        Application application = new Application();
        ProjectWindow projectWindow = new ProjectWindow(application);
        SwingUtilities.invokeLater(projectWindow);
    }

}
