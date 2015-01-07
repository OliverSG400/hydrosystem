package edu.khai.k105.hydrosystem.application.project.circuit.element;

import edu.khai.k105.hydrosystem.application.project.circuit.Circuit;
import edu.khai.k105.hydrosystem.application.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class AccumulatorElement extends Element {

    @XmlAttribute
    private double maxPressure;
    @XmlAttribute
    private double minVolume;
    @XmlAttribute
    private double minPressure;
    @XmlAttribute
    private double maxVolume;
    @XmlAttribute
    private double politropa;

    @Override
    public double deltaP(double pumpQ, Fluid fluid, double gravityAcceleration) {
//        for i:=1 to _j do begin
//        Sum_W_before_accumulator[i]:= W[i];
//        end;
        return 0;
    }

    @Override
    public String toString() {
        return "Аккумулятор";
    }

    public double getMaxPressure() {
        return maxPressure;
    }

    public void setMaxPressure(double maxPressure) {
        this.maxPressure = maxPressure;
    }

    public double getPolitropa() {
        return politropa;
    }

    public void setPolitropa(double politropa) {
        this.politropa = politropa;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }

    public double getMinPressure() {
        return minPressure;
    }

    public void setMinPressure(double minPressure) {
        this.minPressure = minPressure;
    }

    public double getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(double minVolume) {
        this.minVolume = minVolume;
    }
}
