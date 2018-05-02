package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.acquisition.repositories.LastRecordRepository;

public interface FilenameProviderFactory {

	FilenameProvider create(LastRecordRepository lastRecordRepository, DateTimeProvider dateTimeProvider);

}
