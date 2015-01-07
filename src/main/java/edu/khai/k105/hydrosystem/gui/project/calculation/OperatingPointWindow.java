package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.graph.GraphModel;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;

import javax.swing.*;
import java.awt.*;

public class OperatingPointWindow extends JFrame {

    OperatingPointViewer operatingPointViewer;

    public OperatingPointWindow(GraphModel graphModel, GraphPoint operatingPoint) throws HeadlessException {
        setTitle("Рабочая точка");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        operatingPointViewer = new OperatingPointViewer(graphModel, operatingPoint);
        setContentPane(operatingPointViewer.getContentPane());
        pack();
        setLocationRelativeTo(null);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
    }


}
