package br.inpe.climaespacial.swd.kp.forecast.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;

public interface KPForecastEntityMapper {

    List<KPForecastEntity> map(List<KPForecast> kpForecastList);

}
