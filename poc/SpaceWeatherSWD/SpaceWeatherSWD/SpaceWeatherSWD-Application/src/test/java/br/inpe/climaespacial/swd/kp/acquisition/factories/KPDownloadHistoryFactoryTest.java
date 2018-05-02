package br.inpe.climaespacial.swd.kp.acquisition.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultKPDownloadHistoryFactory.class, 
    KPDownloadHistory.class
})
public class KPDownloadHistoryFactoryTest {

    @Inject
    private KPDownloadHistoryFactory kpDownloadHistoryFactory;
    
    @Test
    public void create_called_returns() {        
        KPDownloadHistory kpdh = kpDownloadHistoryFactory.create();
        
        assertNotNull(kpdh); 
        assertEquals(KPDownloadHistory.class, kpdh.getClass());
    }
}
