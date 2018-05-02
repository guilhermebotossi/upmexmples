package br.inpe.climaespacial.swd.values.bx.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;

public interface AverageBXFactory {

	AverageBX create(ZonedDateTime timeTag, Double averageBX);


}
