package br.inpe.climaespacial.swd.kp.forecast.repositories;

import java.util.List;

import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;

public interface KPForecastWriterRepository {

    void save(List<KPForecast> kpForecastList);

}
