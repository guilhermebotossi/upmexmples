package br.inpe.climaespacial.swd.average.validators;

import java.time.ZonedDateTime;

public interface NextHourValidator {

	boolean validate(ZonedDateTime zdt);

}
