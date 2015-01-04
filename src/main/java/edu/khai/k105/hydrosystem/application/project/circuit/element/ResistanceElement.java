package edu.khai.k105.hydrosystem.application.project.circuit.element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ResistanceElement extends Element {

    @XmlAttribute
    private String type;
    @XmlAttribute
    private float coefficient;
    @XmlAttribute
    private float inputDiameter;

    @Override
    public String toString() {
        return "Местное сопротивление";
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public float getInputDiameter() {
        return inputDiameter;
    }

    public void setInputDiameter(float inputDiameter) {
        this.inputDiameter = inputDiameter;
    }
}
