package edu.khai.k105.hydrosystem.gui.project.editor;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Pump;
import edu.khai.k105.hydrosystem.gui.graph.GraphEditor;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphModel;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphSeries;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Oliver on 03.01.2015.
 */
public class PumpEditor {
    private JButton pumpCharacteristicButton;
    private JPanel contentPane;
    private Pump pump;

    public PumpEditor(final Pump pump) {
        this.pump = pump;
        pumpCharacteristicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGraphEditor(pump.getPumpCharacteristic(), "Характеристика насоса");
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JButton getPumpCharacteristicButton() {
        return pumpCharacteristicButton;
    }

    public void setPumpCharacteristicButton(JButton pumpCharacteristicButton) {
        this.pumpCharacteristicButton = pumpCharacteristicButton;
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

}
