package br.inpe.climaespacial.swd.values.bz.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;

@Dependent
public class DefaultBZFactory implements BZFactory {

	@Override
	public BZ create(ZonedDateTime timeTag, Double value) {
		BZ bz = new BZ();
		bz.setTimeTag(timeTag);
		bz.setValue(value);
		return bz;
	}

}
