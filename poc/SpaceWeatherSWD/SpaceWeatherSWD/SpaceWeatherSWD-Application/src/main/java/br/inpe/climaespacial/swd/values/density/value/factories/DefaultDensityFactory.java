package br.inpe.climaespacial.swd.values.density.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.density.value.dtos.Density;

@Dependent
public class DefaultDensityFactory implements DensityFactory {

	@Override
	public Density create(ZonedDateTime timeTag, Double value) {
		Density density = new Density();
		density.setTimeTag(timeTag);
		density.setValue(value);
		return density;
	}

}
