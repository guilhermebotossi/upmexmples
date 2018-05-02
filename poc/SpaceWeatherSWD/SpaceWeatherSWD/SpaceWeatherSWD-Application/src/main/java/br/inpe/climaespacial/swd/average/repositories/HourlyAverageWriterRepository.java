package br.inpe.climaespacial.swd.average.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;

public interface HourlyAverageWriterRepository {

	void save(HourlyAverage hourlyAverage);

}
