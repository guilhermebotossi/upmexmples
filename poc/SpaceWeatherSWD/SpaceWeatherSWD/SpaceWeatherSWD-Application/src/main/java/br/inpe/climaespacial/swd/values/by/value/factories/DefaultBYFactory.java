package br.inpe.climaespacial.swd.values.by.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.by.value.dtos.BY;

@Dependent
public class DefaultBYFactory implements BYFactory {

	@Override
	public BY create(ZonedDateTime timeTag, Double value) {
		BY by = new BY();
		by.setTimeTag(timeTag);
		by.setValue(value);
		return by;
	}

}
