package edu.khai.k105.hydrosystem.dataModel.project;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Circuit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {

    private String title;
    private String description;
    private Circuit circuit;

    public Project() {
        circuit = new Circuit();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public String toString() {
        return title;
    }
}
