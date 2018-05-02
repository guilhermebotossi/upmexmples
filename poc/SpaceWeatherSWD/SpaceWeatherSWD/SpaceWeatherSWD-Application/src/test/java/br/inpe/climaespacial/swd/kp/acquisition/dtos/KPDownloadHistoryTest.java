package br.inpe.climaespacial.swd.kp.acquisition.dtos;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPDownloadHistory.class)
public class KPDownloadHistoryTest {
    
    @Inject
    private KPDownloadHistory kpDownloadHistory;

    @Test
    public void period_called() {
        ZonedDateTime period = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        
        kpDownloadHistory.setPeriod(period);
        
        assertSame(period, kpDownloadHistory.getPeriod());
    }
    
    @Test
    public void complete_called() {
        kpDownloadHistory.setComplete(true);
        assertTrue(kpDownloadHistory.isComplete());
    }
}
