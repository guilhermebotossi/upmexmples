package br.inpe.climaespacial.swd.values.by.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;

public interface AverageBYFactory {

	AverageBY create(ZonedDateTime timeTag, Double averageBY);


}
