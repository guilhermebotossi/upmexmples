package br.inpe.climaespacial.swd.acquisition.providers;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class DefaultDateTimeProvider implements DateTimeProvider {

	@Override
	public ZonedDateTime getNow() {
		return ZonedDateTime.now();
	}

}
