package br.inpe.climaespacial.swd.calculation.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;

@Dependent
public class DefaultCalculatedValuesFactory implements CalculatedValuesFactory {

	@Override
	public CalculatedValues create(Double ey, Double dpr, Double rmp, ZonedDateTime timeTag) {
		CalculatedValues cv = new CalculatedValues();
		cv.setEy(ey);
		cv.setDpr(dpr);
		cv.setRmp(rmp);
		cv.setTimeTag(timeTag);

		return cv;
	}

}
