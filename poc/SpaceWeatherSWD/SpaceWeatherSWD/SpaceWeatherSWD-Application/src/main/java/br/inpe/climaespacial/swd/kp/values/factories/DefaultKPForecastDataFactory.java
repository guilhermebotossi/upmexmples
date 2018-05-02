package br.inpe.climaespacial.swd.kp.values.factories;

import java.util.List;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastData;
import br.inpe.climaespacial.swd.kp.values.dtos.BaseKPForecastEntry;

@Dependent
public class DefaultKPForecastDataFactory implements KPForecastDataFactory {

    @Override
    public KPForecastData create(List<BaseKPForecastEntry> kpForecastEntryList) {
        KPForecastData kpForecastData = new KPForecastData();
        kpForecastData.setKpForecastEntries(kpForecastEntryList);

        return kpForecastData;
    }

}
