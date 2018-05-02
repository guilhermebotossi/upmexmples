package br.inpe.climaespacial.swd.kp.values.dtos;

import javax.enterprise.context.Dependent;

@Dependent
public class KPValueEntry extends BaseKPForecastEntry {
    
    private Double value;
    
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
