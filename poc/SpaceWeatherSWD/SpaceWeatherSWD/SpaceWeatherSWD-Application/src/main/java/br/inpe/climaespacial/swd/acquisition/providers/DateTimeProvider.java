package br.inpe.climaespacial.swd.acquisition.providers;

import java.time.ZonedDateTime;

public interface DateTimeProvider {

	ZonedDateTime getNow();

}
