package br.inpe.climaespacial.swd.values.bt.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;

@Dependent
public class DefaultAverageBTFactory implements AverageBTFactory {

	@Override
	public AverageBT create(ZonedDateTime timeTag, Double value) {
		AverageBT averageBt = new AverageBT();
		averageBt.setTimeTag(timeTag);
		averageBt.setValue(value);
		return averageBt;
	}

}
