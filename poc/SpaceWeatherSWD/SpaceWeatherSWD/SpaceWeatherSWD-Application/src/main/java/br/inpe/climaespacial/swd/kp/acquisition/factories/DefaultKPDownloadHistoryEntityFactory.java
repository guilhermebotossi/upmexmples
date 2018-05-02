package br.inpe.climaespacial.swd.kp.acquisition.factories;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.acquisition.entities.KPDownloadHistoryEntity;

@Dependent
public class DefaultKPDownloadHistoryEntityFactory extends DefaultEntityFactory<KPDownloadHistoryEntity>  implements KPDownloadHistoryEntityFactory {
    
    @Inject
    private HelperFactory<KPDownloadHistoryEntity> KPDownloadHistoryEntityHelperFactory;

    @Override
    protected KPDownloadHistoryEntity doCreate() {
        return KPDownloadHistoryEntityHelperFactory.create();
    }

}
