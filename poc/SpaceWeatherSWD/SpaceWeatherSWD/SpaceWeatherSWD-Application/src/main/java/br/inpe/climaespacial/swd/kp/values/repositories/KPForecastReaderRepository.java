package br.inpe.climaespacial.swd.kp.values.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;

public interface KPForecastReaderRepository {

    List<KPForecast> getNextForecasts(ZonedDateTime zonedDateTime);

}
