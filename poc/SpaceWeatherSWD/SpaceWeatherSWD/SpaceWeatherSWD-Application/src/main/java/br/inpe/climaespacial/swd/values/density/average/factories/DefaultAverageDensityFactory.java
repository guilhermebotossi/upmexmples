package br.inpe.climaespacial.swd.values.density.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;

@Dependent
public class DefaultAverageDensityFactory implements AverageDensityFactory {

	@Override
	public AverageDensity create(ZonedDateTime timeTag, Double value) {
		AverageDensity averageDensity = new AverageDensity();
		averageDensity.setTimeTag(timeTag);
		averageDensity.setValue(value);
		return averageDensity;
	}

}
