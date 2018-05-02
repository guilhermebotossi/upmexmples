package br.inpe.climaespacial.swd.kp.values.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;

public interface KPForecastMapper {

    List<KPForecast> map(List<KPForecastEntity> kpForecastEntityList);

}
