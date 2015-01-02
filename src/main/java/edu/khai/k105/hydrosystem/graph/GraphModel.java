package edu.khai.k105.hydrosystem.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphModel {

    private Map<String, Object> meta = new HashMap<String, Object>();
    private List<GraphSeries> series = new ArrayList<GraphSeries>();

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public List<GraphSeries> getSeries() {
        return series;
    }

    public void setSeries(List<GraphSeries> series) {
        this.series = series;
    }

    public void addSeries(GraphSeries series) {
        this.series.add(series);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphModel)) return false;

        GraphModel that = (GraphModel) o;

        if (!meta.equals(that.meta)) return false;
        if (!series.equals(that.series)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = meta.hashCode();
        result = 31 * result + series.hashCode();
        return result;
    }

}
