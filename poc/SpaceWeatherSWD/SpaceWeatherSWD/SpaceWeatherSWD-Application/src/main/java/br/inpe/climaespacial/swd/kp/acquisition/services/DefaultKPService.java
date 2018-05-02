package br.inpe.climaespacial.swd.kp.acquisition.services;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;
import br.inpe.climaespacial.swd.kp.acquisition.factories.KPDownloadHistoryFactory;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.KPDownloadHistoryWriterRepository;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.KPReaderRepository;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.KPWriterRepository;

@Dependent
public class DefaultKPService implements KPService {

    @Inject
    private KPReaderRepository kpReaderRepository;

    @Inject
    private KPWriterRepository kpWriterRepository;

    @Inject
    private DateTimeProvider dateTimeProvider;

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Inject
    private KPDownloadHistoryFactory kpDownloadHistoryFactory;

    @Inject
    private KPDownloadHistoryWriterRepository kpDownloadHistoryWriterRepository;

    @Override
    @Transactional(value = TxType.NOT_SUPPORTED)
    public void acquire() {
        ZonedDateTime now = dateTimeProvider.getNow();
        ZonedDateTime truncatedNow = dateTimeHelper.truncateToDays(now);
        ZonedDateTime truncatedTimeTag = null;

        do {
            List<KP> kpl = kpReaderRepository.read();
            kpWriterRepository.save(kpl);

            KP kp = kpl.get(0);
            truncatedTimeTag = dateTimeHelper.truncateToDays(kp.getTimeTag());

            KPDownloadHistory kpdh = kpDownloadHistoryFactory.create();
            kpdh.setPeriod(truncatedTimeTag);
            kpdh.setComplete(truncatedNow.isEqual(truncatedTimeTag) ? false : true);

            kpDownloadHistoryWriterRepository.save(kpdh);
        } while (!truncatedTimeTag.isEqual(truncatedNow));
    }

}
