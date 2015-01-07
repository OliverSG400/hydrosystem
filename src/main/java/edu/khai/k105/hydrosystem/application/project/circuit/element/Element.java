package edu.khai.k105.hydrosystem.application.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public abstract class Element {

    public abstract double deltaP(double pumpQ, Fluid fluid, double gravityAcceleration);

}
