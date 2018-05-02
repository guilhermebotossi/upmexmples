package br.inpe.climaespacial.swd.values.by.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.by.value.dtos.BY;

public interface BYFactory {

	BY create(ZonedDateTime timeTag, Double by);


}
