package br.inpe.climaespacial.swd.values.rmp.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;

@Dependent
public class DefaultRMPFactory implements RMPFactory {

	@Override
	public RMP create(ZonedDateTime timeTag, Double value) {
		RMP rmp = new RMP();
		rmp.setTimeTag(timeTag);
		rmp.setValue(value);
		return rmp;
	}

}
