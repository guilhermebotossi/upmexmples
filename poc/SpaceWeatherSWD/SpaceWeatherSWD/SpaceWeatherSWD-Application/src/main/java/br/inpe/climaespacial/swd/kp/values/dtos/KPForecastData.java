package br.inpe.climaespacial.swd.kp.values.dtos;

import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class KPForecastData {
    
    private List<BaseKPForecastEntry> kpForecastEntries;

    public List<BaseKPForecastEntry> getKpForecastEntries() {
        return kpForecastEntries;
    }

    public void setKpForecastEntries(List<BaseKPForecastEntry> kpForecastEntries) {
        this.kpForecastEntries = kpForecastEntries;
    }

}
