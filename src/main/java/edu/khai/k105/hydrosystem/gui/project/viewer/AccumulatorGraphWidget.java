package edu.khai.k105.hydrosystem.gui.project.viewer;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphModel;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphPoint;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphSeries;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;
import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.gui.graph.JGraph;

import javax.swing.*;
import java.awt.*;

public class AccumulatorGraphWidget implements UpdateAble {

    private JPanel contentPane;
    private JGraph graphPanel;
    private Circuit circuit;

    public AccumulatorGraphWidget(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public void updateDataModel(GraphStage mechanismStage) {
        GraphModel graphModel = new GraphModel();
        GraphSeries beforeAccumulatorGraph = circuit.pressureBeforeAccumulator(mechanismStage);
        beforeAccumulatorGraph.getMeta().put(GraphSeries.SERIES_COLOR, Color.RED);
        GraphSeries afterAccumulatorGraph = circuit.pressureLossesAfterAccumulator(mechanismStage);
        afterAccumulatorGraph.getMeta().put(GraphSeries.SERIES_COLOR, Color.BLUE);
        GraphSeries intersection = new GraphSeries();
        GraphPoint operationPointConsideringAccumulator = circuit.operatingPointConsideringAccumulator(mechanismStage);
        intersection.getPoints().add(operationPointConsideringAccumulator);
        graphModel.addSeries(beforeAccumulatorGraph);
        graphModel.addSeries(afterAccumulatorGraph);
        graphModel.addSeries(intersection);
        graphPanel.setModel(graphModel);
        graphPanel.setInfiniteVerticalLine(operationPointConsideringAccumulator);
        double accumulatorResult = 0;
        try {
            accumulatorResult = circuit.systemFlowRateConsideringAccumulator(mechanismStage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        graphPanel.setInfiniteVerticalLine(new GraphPoint(accumulatorResult, 0));
        graphPanel.adaptScale();
        graphPanel.updateUI();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
