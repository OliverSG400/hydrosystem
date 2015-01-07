package edu.khai.k105.hydrosystem.application.project.circuit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Fluid {

    /**
     * Коэффициент кинематической вязкости
     * https://ru.wikipedia.org/wiki/%D0%92%D1%8F%D0%B7%D0%BA%D0%BE%D1%81%D1%82%D1%8C
     */
    @XmlAttribute
    private float kinematicalViscosity;

    /**
     * Удельный вес
     * https://ru.wikipedia.org/wiki/%D0%A3%D0%B4%D0%B5%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9_%D0%B2%D0%B5%D1%81
     */
    @XmlAttribute
    private float specificWeight;

    @Override
    public String toString() {
        return "Рабочая жидкость";
    }

    public float getKinematicalViscosity() {
        return kinematicalViscosity;
    }

    public void setKinematicalViscosity(float kinematicalViscosity) {
        this.kinematicalViscosity = kinematicalViscosity;
    }

    public float getSpecificWeight() {
        return specificWeight;
    }

    public void setSpecificWeight(float specificWeight) {
        this.specificWeight = specificWeight;
    }
}
