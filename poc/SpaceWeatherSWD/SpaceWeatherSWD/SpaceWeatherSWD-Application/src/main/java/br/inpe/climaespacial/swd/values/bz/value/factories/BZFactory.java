package br.inpe.climaespacial.swd.values.bz.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;

public interface BZFactory {

	BZ create(ZonedDateTime timeTag, Double bz);


}
