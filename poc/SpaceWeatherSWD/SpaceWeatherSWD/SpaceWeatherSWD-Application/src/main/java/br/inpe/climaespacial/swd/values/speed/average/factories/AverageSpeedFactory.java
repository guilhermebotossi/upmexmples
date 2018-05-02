package br.inpe.climaespacial.swd.values.speed.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;

public interface AverageSpeedFactory {

	AverageSpeed create(ZonedDateTime timeTag, Double AverageSpeed);


}
