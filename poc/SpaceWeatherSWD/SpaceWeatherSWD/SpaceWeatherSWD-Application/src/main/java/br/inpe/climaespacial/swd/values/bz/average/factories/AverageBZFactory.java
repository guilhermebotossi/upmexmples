package br.inpe.climaespacial.swd.values.bz.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;

public interface AverageBZFactory {

	AverageBZ create(ZonedDateTime timeTag, Double averageBZ);


}
