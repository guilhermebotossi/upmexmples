package br.inpe.climaespacial.swd.kp.values.factories;

import java.util.List;

import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastData;
import br.inpe.climaespacial.swd.kp.values.dtos.BaseKPForecastEntry;

public interface KPForecastDataFactory {

    KPForecastData create(List<BaseKPForecastEntry> kpForecastEntryList);

}
