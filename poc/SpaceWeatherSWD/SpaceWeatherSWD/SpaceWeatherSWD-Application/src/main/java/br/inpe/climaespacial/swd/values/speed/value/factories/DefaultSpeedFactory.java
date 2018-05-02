package br.inpe.climaespacial.swd.values.speed.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;

@Dependent
public class DefaultSpeedFactory implements SpeedFactory {

	@Override
	public Speed create(ZonedDateTime timeTag, Double value) {
		Speed speed = new Speed();
		speed.setTimeTag(timeTag);
		speed.setValue(value);
		return speed;
	}

}
