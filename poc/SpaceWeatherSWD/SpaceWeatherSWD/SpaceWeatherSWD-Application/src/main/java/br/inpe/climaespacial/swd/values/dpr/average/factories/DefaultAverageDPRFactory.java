package br.inpe.climaespacial.swd.values.dpr.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;

@Dependent
public class DefaultAverageDPRFactory implements AverageDPRFactory {

	@Override
	public AverageDPR create(ZonedDateTime timeTag, Double value) {
		AverageDPR averageDPR = new AverageDPR();
		averageDPR.setTimeTag(timeTag);
		averageDPR.setValue(value);
		return averageDPR;
	}

}
