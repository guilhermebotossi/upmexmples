package br.inpe.climaespacial.swd.kp.forecast.services;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;

import java.util.List;

public interface KPForecastCalculator {

    List<KPForecast> calculate(List<CIndex> cIndexList, KPValue kp);

}
