package edu.khai.k105.hydrosystem.hydraulic.element;

import edu.khai.k105.hydrosystem.graph.GraphSeries;
import edu.khai.k105.hydrosystem.hydraulic.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class MechanismElement extends Element {

    @XmlAttribute
    private float pistonSquare;
    @XmlAttribute
    private float outPower; //maybe calculated field
    @XmlAttribute
    private float runPorsh; //maybe calculated field
    private GraphSeries variableLoadCharacteristic;

}
