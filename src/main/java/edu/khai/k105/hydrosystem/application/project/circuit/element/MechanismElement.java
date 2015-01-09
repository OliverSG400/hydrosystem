package edu.khai.k105.hydrosystem.application.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.application.project.graph.GraphStage;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSection;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class MechanismElement extends Element {

    @XmlAttribute
    private float pistonSquare;
    private GraphSeries variableLoadCharacteristic = new GraphSeries();

    @Override
    public String toString() {
        return "Исполнительный механизм";
    }

    public float getPistonSquare() {
        return pistonSquare;
    }

    public void setPistonSquare(float pistonSquare) {
        this.pistonSquare = pistonSquare;
    }

    public GraphSeries getVariableLoadCharacteristic() {
        return variableLoadCharacteristic;
    }

    @Override
    public double deltaP(GraphStage mechanismStage, double pumpQ, Fluid fluid, double gravityAcceleration) {
//        DeltaP:=R[i]/F;
//
//        Th[k]:=DeltaP;
//        W[k]:=W[K]+DeltaP;
        double deltaP = mechanismStage.getValue() / pistonSquare;
        return deltaP;
    }

    public List<GraphStage> getStageGraph() {
        List<GraphStage> stageGraph = new ArrayList<>();
        for (int i = 1; i < variableLoadCharacteristic.getPoints().size(); i++) {
            GraphSection section = new GraphSection(
                    variableLoadCharacteristic.getPoints().get(i - 1),
                    variableLoadCharacteristic.getPoints().get(i));
            double value = Math.min(section.getA().y, section.getB().y) + (Math.abs(section.getA().y - section.getB().y) / 2);
            GraphStage candle = new GraphStage(value, section);
            stageGraph.add(candle);
        }
        return stageGraph;
    }

}
