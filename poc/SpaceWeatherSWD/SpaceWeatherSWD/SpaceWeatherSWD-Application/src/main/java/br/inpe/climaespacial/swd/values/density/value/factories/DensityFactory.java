package br.inpe.climaespacial.swd.values.density.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.density.value.dtos.Density;

public interface DensityFactory {

	Density create(ZonedDateTime timeTag, Double density);


}
