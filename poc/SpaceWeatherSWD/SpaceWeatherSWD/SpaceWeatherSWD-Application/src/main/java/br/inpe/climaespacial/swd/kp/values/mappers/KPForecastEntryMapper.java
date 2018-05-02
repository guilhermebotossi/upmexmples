package br.inpe.climaespacial.swd.kp.values.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastEntry;

public interface KPForecastEntryMapper {

    List<KPForecastEntry> mapKPForecast(List<KPForecast> kpForecastList);

}
