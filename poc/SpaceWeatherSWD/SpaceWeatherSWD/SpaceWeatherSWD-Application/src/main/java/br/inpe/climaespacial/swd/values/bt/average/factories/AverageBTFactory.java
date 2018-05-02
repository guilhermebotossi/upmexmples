package br.inpe.climaespacial.swd.values.bt.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;

public interface AverageBTFactory {

	AverageBT create(ZonedDateTime timeTag, Double averageBX);


}
