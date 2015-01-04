package edu.khai.k105.hydrosystem.application.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;

import javax.xml.bind.annotation.*;

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
}
