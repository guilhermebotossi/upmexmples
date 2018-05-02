package br.inpe.climaespacial.swd.values.speed.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;

public interface SpeedFactory {

	Speed create(ZonedDateTime timeTag, Double speed);


}
