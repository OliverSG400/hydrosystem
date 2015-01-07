package edu.khai.k105.hydrosystem.application.project.circuit;

import edu.khai.k105.hydrosystem.application.project.circuit.element.*;
import edu.khai.k105.hydrosystem.application.project.graph.GraphPoint;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSection;
import edu.khai.k105.hydrosystem.application.project.graph.GraphSeries;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Circuit {

    /**
     * Ускорение свободного падения
     * https://ru.wikipedia.org/wiki/%D0%A3%D1%81%D0%BA%D0%BE%D1%80%D0%B5%D0%BD%D0%B8%D0%B5_%D1%81%D0%B2%D0%BE%D0%B1%D0%BE%D0%B4%D0%BD%D0%BE%D0%B3%D0%BE_%D0%BF%D0%B0%D0%B4%D0%B5%D0%BD%D0%B8%D1%8F
     */
    @XmlAttribute
    private float gravityAcceleration = 9.81f;
    private Fluid workingFluid;
    private Pump pump;
    @XmlElementWrapper(name = "circuitElements")
    @XmlElements({
            @XmlElement(name = "pipelineElement", type = PipelineElement.class),
            @XmlElement(name = "resistanceElement", type = ResistanceElement.class),
            @XmlElement(name = "mechanismElement", type = MechanismElement.class),
            @XmlElement(name = "accumulatorElement", type = AccumulatorElement.class)
    })
    private List<Element> elements;

    public Circuit() {
        workingFluid = new Fluid();
        pump = new Pump();
        elements = new ArrayList<Element>() {
            @Override
            public String toString() {
                return "Элементы";
            }
        };
    }

    /**
     * Цепь имеет кривую ака Характеристика системы (вычисляемую)
     */
    public GraphSeries systemCharacteristic() {
        GraphSeries systemCharacteristicSeries = new GraphSeries();
        List<GraphPoint> systemCharacteristic = systemCharacteristicSeries.getPoints();
        for (GraphPoint point : pump.getPumpCharacteristic().getPoints()) {
            double p = 0;
            double q = point.x;
            for (Element element : elements) {
                p += element.deltaP(q, workingFluid, gravityAcceleration);
            }
            systemCharacteristic.add(new GraphPoint(q, p));
        }
        return systemCharacteristicSeries;
    }

    public GraphPoint operatingPoint() {
        GraphSeries systemCharacteristic = systemCharacteristic();
        for (int i = 0; i < getPump().getPumpCharacteristic().getPoints().size() - 1; i++) {
            GraphSection pumpSection = new GraphSection();
            pumpSection.setA(getPump().getPumpCharacteristic().getPoints().get(i));
            pumpSection.setB(getPump().getPumpCharacteristic().getPoints().get(i + 1));
            GraphSection systemSection = new GraphSection();
            systemSection.setA(systemCharacteristic.getPoints().get(i));
            systemSection.setB(systemCharacteristic.getPoints().get(i + 1));
            GraphPoint operatingPoint = checkIntersect(pumpSection, systemSection);
            if (operatingPoint != null) {
                return operatingPoint;
            }
        }
        return null;
    }

    /**
     * http://www.cyberforum.ru/pascalabc/thread589316.html
     * @param first
     * @param second
     * @return
     */
    private GraphPoint checkIntersect(GraphSection first, GraphSection second) {
//      x:=-((x1*y2-x2*y1)*(x4-x3)-(x3*x4-x4*y3)*(x2-x1))/((y1-y2)*(x4-x3)-(y3-y4)*(x2-x1));
//      y:=((y3-y4)*(-x)-(x3*y4-x4*y3))/(x4-x3);
        double x = - ((first.getA().x * first.getB().y - first.getB().x * first.getA().y) * (second.getB().x - second.getA().x)
                - (second.getA().x * second.getB().x - second.getB().x * second.getA().y) * (first.getB().x - first.getA().x))
                / ((first.getA().y - first.getB().y) * (second.getB().x - second.getA().x)
                - (second.getA().y - second.getB().y) * (first.getB().x - first.getA().x));
        double y = ((second.getA().y - second.getB().y) * (-x)
                - (second.getA().x * second.getB().y - second.getB().x * second.getA().y))
                / (second.getB().x - second.getA().x);
        if ((x >= first.getA().x) && (x <= first.getB().x)) {
            return new GraphPoint(x, y);
        } else {
            return null;
        }
    }

    public float getGravityAcceleration() {
        return gravityAcceleration;
    }

    public void setGravityAcceleration(float gravityAcceleration) {
        this.gravityAcceleration = gravityAcceleration;
    }

    public Fluid getWorkingFluid() {
        return workingFluid;
    }

    public void setWorkingFluid(Fluid workingFluid) {
        this.workingFluid = workingFluid;
    }

    public Pump getPump() {
        return pump;
    }

    public void setPump(Pump pump) {
        this.pump = pump;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "Гидроцепь";
    }

}