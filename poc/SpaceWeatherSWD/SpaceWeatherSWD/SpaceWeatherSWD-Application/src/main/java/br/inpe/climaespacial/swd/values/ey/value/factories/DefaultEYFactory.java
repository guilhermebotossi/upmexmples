package br.inpe.climaespacial.swd.values.ey.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;

@Dependent
public class DefaultEYFactory implements EYFactory {

	@Override
	public EY create(ZonedDateTime timeTag, Double value) {
		EY ey = new EY();
		ey.setTimeTag(timeTag);
		ey.setValue(value);
		return ey;
	}

}
