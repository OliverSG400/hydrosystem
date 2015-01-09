package edu.khai.k105.hydrosystem.application.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.project.graph.GraphStage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

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
        if (type != null) {
            return type;
        }
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public double deltaP(GraphStage mechanismStage, double pumpQ, Fluid fluid, double gravityAcceleration) {
        double v = (4 * pumpQ)
                    / (Math.PI * inputDiameter); // здесь был V:=(4*Q[k])/(pi*D);
        double deltaP = (coefficient
                * fluid.getSpecificWeight()
               * Math.pow(v, 2)
        ) / (2 * gravityAcceleration);
        return deltaP;
    }
}
