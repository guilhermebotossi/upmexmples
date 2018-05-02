package br.inpe.climaespacial.swd.values.ey.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;

public interface AverageEYFactory {

	AverageEY create(ZonedDateTime timeTag, Double averageEY);


}
