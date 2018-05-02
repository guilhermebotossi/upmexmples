package br.inpe.climaespacial.swd.acquisition.repositories;

import java.time.ZonedDateTime;

public interface LastRecordRepository {

	ZonedDateTime getLast();

}
