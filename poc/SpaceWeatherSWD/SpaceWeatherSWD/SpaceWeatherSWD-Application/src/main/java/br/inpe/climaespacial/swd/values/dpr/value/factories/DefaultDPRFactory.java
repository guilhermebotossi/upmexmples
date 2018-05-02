package br.inpe.climaespacial.swd.values.dpr.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;

@Dependent
public class DefaultDPRFactory implements DPRFactory {

	@Override
	public DPR create(ZonedDateTime timeTag, Double value) {
		DPR dpr = new DPR();
		dpr.setTimeTag(timeTag);
		dpr.setValue(value);
		return dpr;
	}

}
