package br.inpe.climaespacial.swd.values.bt.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;

@Dependent
public class DefaultBTFactory implements BTFactory {

	@Override
	public BT create(ZonedDateTime timeTag, Double value) {
		BT bt = new BT();
		bt.setTimeTag(timeTag);
		bt.setValue(value);
		return bt;
	}

}
