package edu.khai.k105.hydrosystem.application.project.graph;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class GraphSeries implements Serializable {

    @XmlElementWrapper(name = "metaElements")
    private Map<String, Object> meta = new HashMap<String, Object>();
    @XmlElementWrapper(name = "pointsElements")
    private List<GraphPoint> points = new ArrayList<GraphPoint>();

    public List<GraphPoint> getPoints() {
        return points;
    }

    public void setPoints(List<GraphPoint> points) {
        this.points = points;
    }

    public void addPoint(GraphPoint point) {
        this.points.add(point);
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphSeries)) return false;

        GraphSeries that = (GraphSeries) o;

        if (!meta.equals(that.meta)) return false;
        if (!points.equals(that.points)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = meta.hashCode();
        result = 31 * result + points.hashCode();
        return result;
    }

}
