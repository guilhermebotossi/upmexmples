package br.inpe.climaespacial.swd.values.dpr.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;

public interface DPRFactory {

	DPR create(ZonedDateTime timeTag, Double dpr);


}
