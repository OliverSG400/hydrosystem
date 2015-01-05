package edu.khai.k105.hydrosystem.application.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;

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

    @Override
    public void take(List<GraphPoint> pumpCharacteristic, List<GraphPoint> systemCharacteristic, Circuit circuit) {
        for (int k = 0; k < pumpCharacteristic.size(); k++) {
            double v = (4 * pumpCharacteristic.get(k).x)
                        / (Math.PI * inputDiameter); // здесь был V:=(4*Q[k])/(pi*D);
            double deltaP = (coefficient
                    * circuit.getWorkingFluid().getSpecificWeight()
                    * Math.pow(v, 2)
             ) / (2 * circuit.getGravityAcceleration());
            systemCharacteristic.get(k).x = pumpCharacteristic.get(k).x;
            systemCharacteristic.get(k).y += (float) deltaP;
        }
    }
}
