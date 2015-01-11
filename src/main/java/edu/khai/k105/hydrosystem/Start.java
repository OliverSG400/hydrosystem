package edu.khai.k105.hydrosystem;

import edu.khai.k105.hydrosystem.application.Application;
import edu.khai.k105.hydrosystem.gui.ApplicationWindow;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.exception.DRException;

import javax.swing.*;

public class Start {

    public static void main(String[] args) {
        Application application = new Application();
        ApplicationWindow applicationWindow = new ApplicationWindow(application);
        SwingUtilities.invokeLater(applicationWindow);
    }

}
