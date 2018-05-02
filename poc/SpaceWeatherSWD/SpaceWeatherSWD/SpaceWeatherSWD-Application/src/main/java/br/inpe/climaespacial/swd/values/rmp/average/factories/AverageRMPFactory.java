package br.inpe.climaespacial.swd.values.rmp.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;

public interface AverageRMPFactory {

	AverageRMP create(ZonedDateTime timeTag, Double averageRMP);


}
