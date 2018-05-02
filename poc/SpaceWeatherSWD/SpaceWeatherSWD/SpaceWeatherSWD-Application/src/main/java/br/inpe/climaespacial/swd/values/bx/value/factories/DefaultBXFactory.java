package br.inpe.climaespacial.swd.values.bx.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;

@Dependent
public class DefaultBXFactory implements BXFactory {

	@Override
	public BX create(ZonedDateTime timeTag, Double value) {
		BX bx = new BX();
		bx.setTimeTag(timeTag);
		bx.setValue(value);
		return bx;
	}

}
