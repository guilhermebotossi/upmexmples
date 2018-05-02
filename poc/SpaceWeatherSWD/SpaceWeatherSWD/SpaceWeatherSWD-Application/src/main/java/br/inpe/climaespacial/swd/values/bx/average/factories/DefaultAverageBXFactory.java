package br.inpe.climaespacial.swd.values.bx.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;

@Dependent
public class DefaultAverageBXFactory implements AverageBXFactory {

	@Override
	public AverageBX create(ZonedDateTime timeTag, Double value) {
		AverageBX averageBX = new AverageBX();
		averageBX.setTimeTag(timeTag);
		averageBX.setValue(value);
		return averageBX;
	}

}
