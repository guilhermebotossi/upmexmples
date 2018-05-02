package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.acquisition.providers.DefaultFilenameProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.acquisition.repositories.LastRecordRepository;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;

@Dependent
public class DefaultFilenameProviderFactory implements FilenameProviderFactory {

    @Override
    public FilenameProvider create(LastRecordRepository lastRecordRepository, DateTimeProvider dateTimeProvider) {

        throwIfNull(lastRecordRepository, "lastRecordRepository");

        throwIfNull(dateTimeProvider, "dateTimeProvider");

        return new DefaultFilenameProvider(lastRecordRepository, dateTimeProvider);
    }

}
