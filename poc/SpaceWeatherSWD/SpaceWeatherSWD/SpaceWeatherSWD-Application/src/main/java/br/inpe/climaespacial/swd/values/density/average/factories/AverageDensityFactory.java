package br.inpe.climaespacial.swd.values.density.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;

public interface AverageDensityFactory {

	AverageDensity create(ZonedDateTime timeTag, Double averageDensity);


}
