package br.inpe.climaespacial.swd.average;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;

public interface HourlyAverageCalculator {

	HourlyAverage calculate(ZonedDateTime dateTime, List<MagPlasmaCalculated> magPlasmaCalculatedList);

}
