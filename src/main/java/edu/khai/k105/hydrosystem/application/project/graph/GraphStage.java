package edu.khai.k105.hydrosystem.application.project.graph;


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
        return "(" + base.getA().x + "; " + base.getB().x + ") (" + String.format("%.2f", value) +")";
    }
}
