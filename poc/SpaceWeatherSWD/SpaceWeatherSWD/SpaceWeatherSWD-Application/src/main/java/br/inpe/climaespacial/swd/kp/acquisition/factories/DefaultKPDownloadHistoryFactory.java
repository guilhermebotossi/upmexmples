package br.inpe.climaespacial.swd.kp.acquisition.factories;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;

@Dependent
public class DefaultKPDownloadHistoryFactory implements KPDownloadHistoryFactory {
    
    @Inject
    private HelperFactory<KPDownloadHistory> kpDownloadHistoryHelperFactory;

    @Override
    public KPDownloadHistory create() {
        return kpDownloadHistoryHelperFactory.create();
    }

}
