package edu.khai.k105.hydrosystem.application.project.circuit;

import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Pump {

    private GraphSeries pumpCharacteristic = new GraphSeries();

    @Override
    public String toString() {
        return "Насос";
    }

    public GraphSeries getPumpCharacteristic() {
        return pumpCharacteristic;
    }

}
