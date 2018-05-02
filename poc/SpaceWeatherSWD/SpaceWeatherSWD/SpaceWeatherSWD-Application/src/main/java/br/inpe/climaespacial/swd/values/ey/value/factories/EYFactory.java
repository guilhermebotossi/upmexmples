package br.inpe.climaespacial.swd.values.ey.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;

public interface EYFactory {

	EY create(ZonedDateTime timeTag, Double ey);


}
