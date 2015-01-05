package edu.khai.k105.hydrosystem.application.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;

import javax.xml.bind.annotation.*;
import java.util.List;

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

    @Override
    public void take(List<GraphPoint> pumpCharacteristic, List<GraphPoint> systemCharacteristic, Circuit circuit) {
        for (int k = 0; k < pumpCharacteristic.size(); k++) {
            double deltaP = 0;
            if (pumpCharacteristic.get(k).x != 0) {
                double reynolds = (4 * pumpCharacteristic.get(k).x)
                        / (Math.PI * diameter * circuit.getWorkingFluid().getKinematicalViscosity());
                double lambda = calculateLambda(reynolds);
                deltaP = (lambda
                        * circuit.getWorkingFluid().getSpecificWeight()
                        * length
                        * 16
                        * Math.pow(pumpCharacteristic.get(k).x, 2)
                        )  / (2
                        * circuit.getGravityAcceleration()
                        * Math.pow(diameter, 5)
                        * Math.pow(Math.PI, 2));
            }
            systemCharacteristic.get(k).x = pumpCharacteristic.get(k).x;
            systemCharacteristic.get(k).y += (float) deltaP;
        }
    }

    private double calculateLambda(double reynolds) {
        double lambda = 0;
        if (reynolds < 2300) {
            lambda = 64 / reynolds;
        }
        if ((reynolds <= 100000) && (reynolds >= 2300)) {
            double lnReynolds = (-Math.log(1-reynolds))/reynolds;
            lambda = 0.316 / Math.exp(lnReynolds / 4);
        }
        if (reynolds > 100000) {
            double lnReynolds = (-Math.log(1-reynolds))/reynolds;
            lambda = 0.09 / Math.exp(lnReynolds / 7);
        }
        return lambda;
    }
}
