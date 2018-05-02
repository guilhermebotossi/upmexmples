package br.inpe.climaespacial.swd.values.bx.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;

public interface BXFactory {

	BX create(ZonedDateTime timeTag, Double bx);


}
