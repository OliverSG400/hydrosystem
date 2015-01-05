package edu.khai.k105.hydrosystem.application.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class MechanismElement extends Element {

    @XmlAttribute
    private float pistonSquare;
    @XmlTransient
    private float outPower; //maybe calculated field
    @XmlTransient
    private float runPorsh; //maybe calculated field
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

    public void setVariableLoadCharacteristic(GraphSeries variableLoadCharacteristic) {
        this.variableLoadCharacteristic = variableLoadCharacteristic;
    }

    @Override
    public void take(List<GraphPoint> pumpCharacteristic, List<GraphPoint> systemCharacteristic, Circuit circuit) {
//        DeltaP:=R[i]/F;
//
//        Th[k]:=DeltaP;
//        W[k]:=W[K]+DeltaP;
        MechanismElement mechanism = null;
        for (Element element : circuit.getElements()) {
            if (element instanceof MechanismElement) {
                mechanism = (MechanismElement) element;
            }
        }

        for (int k = 0; k < pumpCharacteristic.size(); k++) {
            double deltaP = mechanism.getVariableLoadCharacteristic().getPoints().get(1).y / pistonSquare;
            systemCharacteristic.get(k).x = pumpCharacteristic.get(k).x;
            systemCharacteristic.get(k).y += (float) deltaP;
        }
    }
}
