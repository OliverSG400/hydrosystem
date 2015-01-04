package edu.khai.k105.hydrosystem.application.project.circuit.element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class PipelineElement extends Element {

    @XmlAttribute
    private float length;
    @XmlAttribute
    private float diameter;

    @Override
    public String toString() {
        return "Трубопровод";
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }
}
