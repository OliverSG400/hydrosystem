package edu.khai.k105.hydrosystem.dataModel.project.circuit.element;

import edu.khai.k105.hydrosystem.dataModel.project.circuit.Fluid;
import edu.khai.k105.hydrosystem.dataModel.graph.GraphStage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class AccumulatorElement extends Element {

    @XmlAttribute
    private double maxPressure;
    @XmlAttribute
    private double minVolume;
    @XmlAttribute
    private double minPressure;
    @XmlAttribute
    private double maxVolume;
    @XmlAttribute
    private double politropa = 1.3;
    @XmlAttribute
    private double initPressure;

    @Override
    public double deltaP(GraphStage mechanismStage, double pumpQ, Fluid fluid, double gravityAcceleration) {
//        for i:=1 to _j do begin
//        Sum_W_before_accumulator[i]:= W[i];
//        end;
        return 0;
    }


//        {Ïîèñê òî÷êè ïåðåñå÷åíèÿ ìåæäó ãðàôèêîì 1 ó÷àñòêà è 2-3}
//
//        {-------------------------------------------------------}
//        i:=2;
//
//        Vi:= x / EnterPoint.F;
//        DeltaTi:= (S[2]-S[1]) / Vi;
//
//        Test1Wmin :=AccumulatorUnit.WAkMin;
//        Test1PMax :=AccumulatorUnit.PAkMax;
//        Test1Wminin13 :=power(AccumulatorUnit.WAkMin, 1.3);
//
//        ShowMessage(FloatToStr(Test1Wmin)); //0,5
//        ShowMessage(FloatToStr(Test1PMax)); //700000
//        ShowMessage(FloatToStr(Test1Wminin13)); //0,40612620
//
//        //îïðåäåëåíèå êîíñòàíòû àêêóìóëÿòîðà
//        {Cak:=(Form1.ListBox1.Items.Objects[AccumulatorUnit.numberInList] as TType7).getPMax *
//                power((Form1.ListBox1.Items.Objects[AccumulatorUnit.numberInList] as TType7).getWMin, 1.3);}
//
//        //Alternate
//        Cak := AccumulatorUnit.PAkMax * power(AccumulatorUnit.WAkMin, 1.3);
//
//        {Ðàñ÷åò àêêóìóëÿòîðà}
//        Pak[i]:= y;
//
//        if Pak[i] <= AccumulatorUnit.PAkMax then
//                begin
//        Wak[i] := power(Cak/Pak[i], -1.3);
//        //---------------------------------
//        if i > 1 then begin //ýòà óñëîâíàÿ ðàìêà äëÿ áëîêèðîâêè âû÷èòàíèÿ èç íå ñóùåñòâóþùåãî çíà÷åíèÿ
//        DeltaW := AccumulatorUnit.WAkMin - Wak[i];
//
//        Qak[i] := DeltaW / DeltaTi;
//
//        QsistNew := x + Qak[i];
//
//        DebugLossesUnit.DebugLossesForm.Series4.AddXY(Qak[i]-1,-1000000);
//        DebugLossesUnit.DebugLossesForm.Series4.AddXY(Qak[i]+1,1000000);
//        end;
//        //---------------------------------
//        end else begin
//        ShowMessage('Äàâëåíèå â àêêóìóëÿòîðå ïðåâûøàåò ìàêñèìàëüíî äîïóñòèìîå');
//        end;
    public double constant() {
    return maxPressure * Math.pow(minVolume, politropa);
}

    public double fluidFlowRate(double previousVolume, double systemPressure, double deltaTime) throws Exception {
        if (systemPressure <= maxPressure) {
            double currentVolume = pressureToVolume(systemPressure);
            double fluidFlowRate = deltaVolume(previousVolume, currentVolume) / deltaTime;
            return fluidFlowRate;
        } else {
            throw new Exception("Давление в аккумуляторе превышает максимальное");
        }
    }

    public double deltaVolume(double previous, double current) {
        return current - previous;
    }

    public double pressureToVolume(double pressure) {
        return Math.pow(constant() / pressure, - politropa);
    }

    public double getInitVolume() {
        return pressureToVolume(initPressure);
    }

    @Override
    public String toString() {
        return "Аккумулятор";
    }

    public double getMaxPressure() {
        return maxPressure;
    }

    public void setMaxPressure(double maxPressure) {
        this.maxPressure = maxPressure;
    }

    public double getPolitropa() {
        return politropa;
    }

    public void setPolitropa(double politropa) {
        this.politropa = politropa;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }

    public double getMinPressure() {
        return minPressure;
    }

    public void setMinPressure(double minPressure) {
        this.minPressure = minPressure;
    }

    public double getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(double minVolume) {
        this.minVolume = minVolume;
    }

    public double getInitPressure() {
        return initPressure;
    }

    public void setInitPressure(double initPressure) {
        this.initPressure = initPressure;
    }
}
