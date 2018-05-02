package br.inpe.climaespacial.swd.average.providers;

import java.time.ZonedDateTime;

public interface HourlyAverageNextHourRepository {

	ZonedDateTime getNextHour();

}
