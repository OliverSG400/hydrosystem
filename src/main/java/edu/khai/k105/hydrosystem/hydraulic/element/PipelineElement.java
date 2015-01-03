package edu.khai.k105.hydrosystem.hydraulic.element;

import edu.khai.k105.hydrosystem.hydraulic.Element;

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

}
