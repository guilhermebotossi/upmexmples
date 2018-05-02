package br.inpe.climaespacial.swd.values.bt.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;

public interface BTFactory {

	BT create(ZonedDateTime timeTag, Double bt);


}
