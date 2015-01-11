package edu.khai.k105.hydrosystem;

import edu.khai.k105.hydrosystem.dataModel.Application;
import edu.khai.k105.hydrosystem.gui.ApplicationWindow;

import javax.swing.*;

public class Start {

    public static void main(String[] args) {
        Application application = new Application();
        ApplicationWindow applicationWindow = new ApplicationWindow(application);
        SwingUtilities.invokeLater(applicationWindow);
    }

}
