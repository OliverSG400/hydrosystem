package edu.khai.k105.hydrosystem.application.project.graph;

import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;

public class GraphSection {

    private GraphPoint a;
    private GraphPoint b;

    public GraphSection() {
    }

    public GraphSection(GraphPoint a, GraphPoint b) {
        this.a = a;
        this.b = b;
    }

    public GraphPoint getA() {
        return a;
    }

    public void setA(GraphPoint a) {
        this.a = a;
    }

    public GraphPoint getB() {
        return b;
    }

    public void setB(GraphPoint b) {
        this.b = b;
    }
}
