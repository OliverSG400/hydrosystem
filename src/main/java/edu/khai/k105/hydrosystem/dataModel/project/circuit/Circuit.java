package edu.khai.k105.hydrosystem.dataModel.project.circuit;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.element.*;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphPoint;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphSection;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphSeries;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;

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

    public GraphSeries systemCharacteristic(GraphStage mechanismStage) {
        GraphSeries systemCharacteristicSeries = new GraphSeries();
        List<GraphPoint> systemCharacteristic = systemCharacteristicSeries.getPoints();
        for (GraphPoint point : pump.getPumpCharacteristic().getPoints()) {
            double p = 0;
            double q = point.x;
            for (Element element : elements) {
                p += element.deltaP(mechanismStage, q, workingFluid, gravityAcceleration);
            }
            systemCharacteristic.add(new GraphPoint(q, p));
        }
        return systemCharacteristicSeries;
    }

    public Double[][] getSumLossesData(GraphStage mechanismStage) {
        Double[][] data = new Double[elements.size()]
                [pump.getPumpCharacteristic().getPoints().size()];
        for (int e = 0; e < elements.size(); e++) {
            for (int q = 0; q < pump.getPumpCharacteristic().getPoints().size(); q++) {
                double pumpQ = pump.getPumpCharacteristic().getPoints().get(q).x;
                double deltaP = elements.get(e).deltaP(mechanismStage, pumpQ, workingFluid, gravityAcceleration);
                if (e > 0) {
                    data[e][q] = data[e - 1][q] + deltaP;
                } else {
                    data[e][q] = deltaP;
                }
            }
        }
        return data;
    }

    public Double[][] getLocalLossesData(GraphStage mechanismStage) {
        Double[][] data = new Double[elements.size()]
                [pump.getPumpCharacteristic().getPoints().size()];
        for (int e = 0; e < elements.size(); e++) {
            for (int q = 0; q < pump.getPumpCharacteristic().getPoints().size(); q++) {
                double pumpQ = pump.getPumpCharacteristic().getPoints().get(q).x;
                data[e][q] = elements.get(e).deltaP(mechanismStage, pumpQ, workingFluid, gravityAcceleration);
            }
        }
        return data;
    }

    public GraphPoint operatingPoint(GraphStage mechanismStage) {
        return graphSeriesIntersect(systemCharacteristic(mechanismStage), getPump().getPumpCharacteristic(), mechanismStage);
    }

    public double responseTime() {
        double totalTime = 0;
        for (GraphStage stage : getMechanism().getStageGraph()) {
               totalTime += responseTime(operatingPoint(stage).x, stage);
        }
        return totalTime;
    }

    public double responseTimeConsiderAccumulator() throws Exception {
        double previousAccumulatorVolume = getAccumulator().getInitVolume();
        double responseTime = 0;
        for (GraphStage mechanismStage : getMechanism().getStageGraph()) {
            GraphPoint operatingPoint = operatingPointConsideringAccumulator(mechanismStage);
            double accumulatorFlowRate = accumulatorFlowRate(mechanismStage, operatingPoint.y, previousAccumulatorVolume);
            double resultFlowRate = operatingPoint.x + accumulatorFlowRate;
            responseTime += responseTime(resultFlowRate, mechanismStage);
            previousAccumulatorVolume += getAccumulator().deltaVolume(previousAccumulatorVolume, getAccumulator().pressureToVolume(operatingPoint.y));
        }
        return responseTime;
    }

    public double systemFlowRateConsideringAccumulator(GraphStage mechanismStage) throws Exception {
        double previousAccumulatorVolume = getAccumulator().getInitVolume();
        for (GraphStage currentStage : getMechanism().getStageGraph()) {
            GraphPoint operatingPoint = operatingPointConsideringAccumulator(currentStage);
            double accumulatorFlowRate = accumulatorFlowRate(currentStage, operatingPoint.y, previousAccumulatorVolume);
            double resultFlowRate = operatingPoint.x + accumulatorFlowRate;
            previousAccumulatorVolume += getAccumulator().deltaVolume(previousAccumulatorVolume, getAccumulator().pressureToVolume(operatingPoint.y));
            if (currentStage.getBase().getA().equals(mechanismStage.getBase().getA())) {
                return resultFlowRate;
            }
        }
        throw new Exception("Участки переменной нагрузки не совпали");
    }

    public double accumulatorFlowRate(GraphStage mechanismStage, double systemPressure, double previousStageAccumulatorVolume) throws Exception {
        return getAccumulator().fluidFlowRate(previousStageAccumulatorVolume,
                systemPressure, responseTime(systemPressure, mechanismStage));
    }

    public double responseTime(double flowRate, GraphStage mechanismStage) {
        return mechanismStage.getBase().distanceBetweenPointsX() / (flowRate / getMechanism().getPistonSquare());
    }

    public GraphSeries pressureBeforeAccumulator(GraphStage mechanismStage) {
        GraphSeries pressureBeforeAccumulatorSeries = new GraphSeries();
        List<GraphPoint> pressureBeforeAccumulator = pressureBeforeAccumulatorSeries.getPoints();
        for (GraphPoint point : pump.getPumpCharacteristic().getPoints()) {
            double p = 0;
            double q = point.x;
            for (int index = 0; index < getAccumulatorElementIndex(); index++) {
                p += elements.get(index).deltaP(mechanismStage, q, workingFluid, gravityAcceleration);
            }
            pressureBeforeAccumulator.add(new GraphPoint(q, point.y - p));
        }
        return pressureBeforeAccumulatorSeries;
    }

    public GraphSeries pressureLossesAfterAccumulator(GraphStage mechanismStage) {
        GraphSeries pressureLossesAfterAccumulatorSeries = new GraphSeries();
        List<GraphPoint> pressureLossesAfterAccumulator = pressureLossesAfterAccumulatorSeries.getPoints();
        for (GraphPoint point : pump.getPumpCharacteristic().getPoints()) {
            double p = 0;
            double q = point.x;
            for (int index = getAccumulatorElementIndex() + 1; index < elements.size(); index++) {
                p += elements.get(index).deltaP(mechanismStage, q, workingFluid, gravityAcceleration);
            }
            pressureLossesAfterAccumulator.add(new GraphPoint(q, p));
        }
        return pressureLossesAfterAccumulatorSeries;
    }

    public GraphPoint operatingPointConsideringAccumulator(GraphStage mechanismStage) {
        return graphSeriesIntersect(pressureBeforeAccumulator(mechanismStage),
                pressureLossesAfterAccumulator(mechanismStage), mechanismStage);
    }

    public GraphPoint graphSeriesIntersect(GraphSeries one, GraphSeries another, GraphStage mechanismStage) {
        for (int i = 0; i < one.getPoints().size() - 1; i++) {
            GraphSection oneSection = new GraphSection();
            oneSection.setA(one.getPoints().get(i));
            oneSection.setB(one.getPoints().get(i + 1));
            GraphSection anotherSection = new GraphSection();
            anotherSection.setA(another.getPoints().get(i));
            anotherSection.setB(another.getPoints().get(i + 1));
            GraphPoint operatingPoint = checkIntersect(oneSection, anotherSection);
            if (operatingPoint != null) {
                return operatingPoint;
            }
        }
        return null;
    }





    public MechanismElement getMechanism() {
        for (Element element : elements) {
            if (element instanceof MechanismElement) {
                return (MechanismElement) element;
            }
        }
        return null;
    }

    public AccumulatorElement getAccumulator() {
        for (Element element : elements) {
            if (element instanceof AccumulatorElement) {
                return (AccumulatorElement) element;
            }
        }
        return null;
    }

    public int getAccumulatorElementIndex() {
        return elements.indexOf(getAccumulator());
    }

    private GraphPoint checkIntersect(GraphSection first, GraphSection second) {
        double eps = 0.000001;

        GraphPoint a1 = first.getA();
        GraphPoint a2 = first.getB();
        GraphPoint b1 = second.getA();
        GraphPoint b2 = second.getB();

        double d  = (a1.x-a2.x)*(b2.y-b1.y) - (a1.y-a2.y)*(b2.x-b1.x);
        double da = (a1.x-b1.x)*(b2.y-b1.y) - (a1.y-b1.y)*(b2.x-b1.x);
        double db = (a1.x-a2.x)*(a1.y-b1.y) - (a1.y-a2.y)*(a1.x-b1.x);

        if ( Math.abs(d) < eps) {
            System.out.println("Отрезки параллельны");
        } else {
            double ta = da / d;
            double tb = db / d;
            if ((0 <= ta) && (ta <= 1)
            && (0 <= tb) && (tb <= 1)) {
                return new GraphPoint(a1.x + ta * (a2.x - a1.x), a1.y + ta * (a2.y - a1.y));
            } else {
                System.out.println("Отрезки не пересекаются");
            }
        }
        return null;
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

    public Pump getPump() {
        return pump;
    }

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return "Гидроцепь";
    }

}
