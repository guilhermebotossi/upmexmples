package br.inpe.climaespacial.swd.kp.acquisition.entities;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPDownloadHistoryEntity.class)
public class KPDownloadHistoryEntityTest {
    
    @Inject
    private KPDownloadHistoryEntity kpDownloadHistoryEntity;

    @Test
    public void period_called() {
        ZonedDateTime period = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        
        kpDownloadHistoryEntity.setPeriod(period);
        
        assertSame(period, kpDownloadHistoryEntity.getPeriod());
    }
    
    @Test
    public void complete_called() {
        kpDownloadHistoryEntity.setComplete(true);
        assertTrue(kpDownloadHistoryEntity.isComplete());
    }
}
