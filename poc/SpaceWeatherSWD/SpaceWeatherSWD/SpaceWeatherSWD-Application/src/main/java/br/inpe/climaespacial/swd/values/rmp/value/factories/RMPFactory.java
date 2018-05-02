package br.inpe.climaespacial.swd.values.rmp.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;

public interface RMPFactory {

	RMP create(ZonedDateTime timeTag, Double rmp);


}
