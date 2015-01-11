package edu.khai.k105.hydrosystem.dataModel.project.circuit.element;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public abstract class Element {

    public abstract double deltaP(GraphStage mechanismStage, double pumpQ, Fluid fluid, double gravityAcceleration);

}
