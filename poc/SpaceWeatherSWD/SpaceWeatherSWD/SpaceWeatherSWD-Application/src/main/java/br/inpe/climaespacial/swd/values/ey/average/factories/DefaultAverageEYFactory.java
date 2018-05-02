package br.inpe.climaespacial.swd.values.ey.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;

@Dependent
public class DefaultAverageEYFactory implements AverageEYFactory {

	@Override
	public AverageEY create(ZonedDateTime timeTag, Double value) {
		AverageEY averageEY = new AverageEY();
		averageEY.setTimeTag(timeTag);
		averageEY.setValue(value);
		return averageEY;
	}

}
