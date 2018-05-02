package br.inpe.climaespacial.swd.values.rmp.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;

@Dependent
public class DefaultAverageRMPFactory implements AverageRMPFactory {

	@Override
	public AverageRMP create(ZonedDateTime timeTag, Double value) {
		AverageRMP averageRMP = new AverageRMP();
		averageRMP.setTimeTag(timeTag);
		averageRMP.setValue(value);
		return averageRMP;
	}

}
