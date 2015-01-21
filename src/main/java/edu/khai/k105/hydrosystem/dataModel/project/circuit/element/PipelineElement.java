package edu.khai.k105.hydrosystem.dataModel.project.circuit.element;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class PipelineElement extends Element {

    @XmlAttribute
    private double length;
    @XmlAttribute
    private double diameter;

    @Override
    public String toString() {
        return "Трубопровод";
    }

    public double getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    @Override
    public double deltaP(GraphStage mechanismStage, double pumpQ, Fluid fluid, double gravityAcceleration) {
        double deltaP = 0;
        if (pumpQ != 0) {
            double reynolds = (4 * pumpQ)
                    / (Math.PI * diameter * fluid.getKinematicalViscosity());
            double lambda = calculateLambda(reynolds);
            deltaP = (lambda
                    * fluid.getSpecificWeight()
                    * length
                    * 16
                    * Math.pow(pumpQ, 2)
                    )  / (2
                    * gravityAcceleration
                    * Math.pow(diameter, 5)
                    * Math.pow(Math.PI, 2));
         }
        return deltaP;
    }

    private double calculateLambda(double reynolds) {
        double lambda = 0;
        if (reynolds < 2300) {
            lambda = 64 / reynolds;
        }
        if ((reynolds <= 100000) && (reynolds >= 2300)) {
            lambda = 0.316 / Math.exp(Math.log(reynolds) / 4);
            System.out.println("lambda = " + lambda);
        }
        if (reynolds > 100000) {
            double lnReynolds = (-Math.log(1-reynolds))/reynolds;
            lambda = 0.09 / Math.exp(Math.log(reynolds) / 7);
        }
        return lambda;
    }
}
