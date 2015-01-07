package edu.khai.k105.hydrosystem.gui.project.calculation;

import edu.khai.k105.hydrosystem.application.project.graph.GraphModel;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;

import javax.swing.*;
import java.awt.*;

public class OperatingPointWindow extends JFrame {

    OperatingPointViewer operatingPointViewer;

    public OperatingPointWindow(GraphModel graphModel, GraphPoint operatingPoint) throws HeadlessException {
        setTitle("Рабочая точка");
        operatingPointViewer = new OperatingPointViewer(graphModel, operatingPoint);
        setContentPane(operatingPointViewer.getContentPane());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
//        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

//        JFrame f = new JFrame(title);
//        GraphModel graphModel = new GraphModel();
//        graphModel.getSeries().add(series);
//        GraphEditor graphEditor = new GraphEditor(graphModel);
//        f.setContentPane(graphEditor.getContentPane());
//        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        f.pack();
//        f.setLocationRelativeTo(null);
//        f.setVisible(true);
    }


}
