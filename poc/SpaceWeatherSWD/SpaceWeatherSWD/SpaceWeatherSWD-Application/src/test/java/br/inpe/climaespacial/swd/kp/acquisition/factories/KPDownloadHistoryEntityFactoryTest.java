package br.inpe.climaespacial.swd.kp.acquisition.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.kp.acquisition.entities.KPDownloadHistoryEntity;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    DefaultEntityFactory.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class,
    HelperFactoryProducer.class,
    DefaultKPDownloadHistoryEntityFactory.class, 
    KPDownloadHistoryEntity.class
})
public class KPDownloadHistoryEntityFactoryTest {

    @Inject
    private KPDownloadHistoryEntityFactory kpDownloadHistoryEntityFactory;
    
    @Test
    public void create_called_returns() {        
        KPDownloadHistoryEntity kpdhe = kpDownloadHistoryEntityFactory.create();
        
        assertNotNull(kpdhe); 
        assertEquals(KPDownloadHistoryEntity.class, kpdhe.getClass());
    }
}
