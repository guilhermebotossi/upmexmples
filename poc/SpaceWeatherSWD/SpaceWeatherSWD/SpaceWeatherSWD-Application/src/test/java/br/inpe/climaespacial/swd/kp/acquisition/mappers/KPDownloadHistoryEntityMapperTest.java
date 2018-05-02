package br.inpe.climaespacial.swd.kp.acquisition.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;
import br.inpe.climaespacial.swd.kp.acquisition.entities.KPDownloadHistoryEntity;
import br.inpe.climaespacial.swd.kp.acquisition.factories.KPDownloadHistoryEntityFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPDownloadHistoryEntityMapper.class)
public class KPDownloadHistoryEntityMapperTest {
    
    @Mock
    @Produces
    private KPDownloadHistoryEntityFactory kpDownloadHistoryEntityFactory;
    
    @Mock
    @Produces
    private DateTimeHelper dateTimeHelper;
    
    @Inject
    private KPDownloadHistoryEntityMapper kpDownloadHistoryEntityMapper;

    @Test
    public void map_calledWithNull_throwsRuntimeException() {
        RuntimeException re = null;

        try {
            kpDownloadHistoryEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"kpDownloadHistory\" cannot be null.", re.getMessage());
    }
    
    
    @Test
    public void map_called_returnsKPEntity() {
        when(kpDownloadHistoryEntityFactory.create()).thenReturn(new KPDownloadHistoryEntity());
        
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-25T13:00:00z[UTC]");
        ZonedDateTime truncatedTimeTag = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
       
        KPDownloadHistory kpdh = new KPDownloadHistory();
        kpdh.setPeriod(timeTag);
        kpdh.setComplete(true);
        
        when(dateTimeHelper.truncateToDays(timeTag)).thenReturn(truncatedTimeTag);
        
        
        KPDownloadHistoryEntity kpdhe = kpDownloadHistoryEntityMapper.map(kpdh);
        
        assertNotNull(kpdhe);
        assertSame(truncatedTimeTag, kpdhe.getPeriod());
        assertTrue(kpdhe.isComplete());
        verify(dateTimeHelper).truncateToDays(timeTag);
        
    }
}
