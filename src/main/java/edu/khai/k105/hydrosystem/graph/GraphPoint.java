package edu.khai.k105.hydrosystem.graph;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class GraphPoint implements Serializable {

    @XmlAttribute
    public float x;

    @XmlAttribute
    public float y;

    public GraphPoint() {
    }

    public GraphPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphPoint graphPoint = (GraphPoint) o;

        if (Float.compare(graphPoint.x, x) != 0) return false;
        if (Float.compare(graphPoint.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" + x + " | " + y + "]";
    }

}
