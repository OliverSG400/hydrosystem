package edu.khai.k105.hydrosystem.dataModel.graph;


public class GraphStage {

    private double value;
    private GraphSection base;

    public GraphStage(double value, GraphSection base) {
        this.value = value;
        this.base = base;
    }

    public GraphSection getBase() {
        return base;
    }

    public void setBase(GraphSection base) {
        this.base = base;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + String.format("%.4f", base.getA().x) + "; " + String.format("%.4f", base.getB().x) + ") (" + String.format("%.2f", value) +")";
    }
}
