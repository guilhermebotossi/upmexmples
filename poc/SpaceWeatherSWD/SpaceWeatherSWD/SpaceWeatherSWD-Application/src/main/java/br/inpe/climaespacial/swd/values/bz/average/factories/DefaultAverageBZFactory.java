package br.inpe.climaespacial.swd.values.bz.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;

@Dependent
public class DefaultAverageBZFactory implements AverageBZFactory {

	@Override
	public AverageBZ create(ZonedDateTime timeTag, Double value) {
		AverageBZ averageBZ = new AverageBZ();
		averageBZ.setTimeTag(timeTag);
		averageBZ.setValue(value);
		return averageBZ;
	}

}
