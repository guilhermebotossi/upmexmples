package br.inpe.climaespacial.swd.kp.acquisition.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;
import br.inpe.climaespacial.swd.kp.acquisition.entities.KPDownloadHistoryEntity;
import br.inpe.climaespacial.swd.kp.acquisition.factories.KPDownloadHistoryEntityFactory;

@Dependent
public class DefaultKPDownloadHistoryEntityMapper implements KPDownloadHistoryEntityMapper {

    @Inject
    private KPDownloadHistoryEntityFactory kpDownloadHistoryEntityFactory;
    
    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public KPDownloadHistoryEntity map(KPDownloadHistory kpDownloadHistory) {
        throwIfNull(kpDownloadHistory, "kpDownloadHistory");
        KPDownloadHistoryEntity kpdhe = kpDownloadHistoryEntityFactory.create();
        
        ZonedDateTime truncatedPeriod = dateTimeHelper.truncateToDays(kpDownloadHistory.getPeriod());
        
        kpdhe.setPeriod(truncatedPeriod);
        kpdhe.setComplete(kpDownloadHistory.isComplete());

        return kpdhe;
    }
}
