package br.inpe.climaespacial.swd.values.dpr.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;

public interface AverageDPRFactory {

	AverageDPR create(ZonedDateTime timeTag, Double averageDPR);


}
