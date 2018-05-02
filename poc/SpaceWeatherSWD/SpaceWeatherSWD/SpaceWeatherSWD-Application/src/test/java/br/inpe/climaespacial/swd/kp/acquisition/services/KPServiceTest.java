package br.inpe.climaespacial.swd.kp.acquisition.services;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;
import br.inpe.climaespacial.swd.kp.acquisition.factories.KPDownloadHistoryFactory;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.KPDownloadHistoryWriterRepository;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.KPReaderRepository;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.KPWriterRepository;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPService.class)
public class KPServiceTest {
    
    @Mock
    @Produces
    private KPReaderRepository kpReaderRepository;
    
    @Mock
    @Produces
    private KPWriterRepository kpWriterRepository;
    
    @Mock
    @Produces
    private DateTimeProvider dateTimeProvider;
    
    @Mock
    @Produces
    private KPDownloadHistoryFactory kpDownloadHistoryFactory;
    
    @Mock
    @Produces
    private KPDownloadHistoryWriterRepository kpDownloadHistoryWriterRepository;
    
    @Mock
    @Produces    
    private DateTimeHelper dateTimeHelper;
     
    @Inject
    private KPService kpService;
    
    
    @Test(timeout = 5000)
    public void acquire_calledWithLoop_succeeds() {
        ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime truncatedNow = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(now);
        when(dateTimeHelper.truncateToDays(now)).thenReturn(truncatedNow);
        
        List<KP> kpl1 = mockList(KP.class); 
        KP kp1 = mock(KP.class);
        when(kpl1.get(0)).thenReturn(kp1);
        ZonedDateTime period1 = ZonedDateTime.parse("2016-11-25T00:00:00z[UTC]");
        when(kp1.getTimeTag()).thenReturn(period1);
        ZonedDateTime truncatedPeriod1 = ZonedDateTime.parse("2016-11-01T00:00:00z[UTC]");
        when(dateTimeHelper.truncateToDays(period1)).thenReturn(truncatedPeriod1);
        
        List<KP> kpl2 = mockList(KP.class);
        KP kp2 = mock(KP.class);
        when(kpl2.get(0)).thenReturn(kp2);
        ZonedDateTime period2 = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
        when(kp2.getTimeTag()).thenReturn(period2);
        when(dateTimeHelper.truncateToDays(period2)).thenReturn(truncatedNow);
        
        when(kpReaderRepository.read()).thenReturn(kpl1) 
            .thenReturn(kpl2);
            
        KPDownloadHistory kpdh1 = mock(KPDownloadHistory.class);
        KPDownloadHistory kpdh2 = mock(KPDownloadHistory.class);
        when(kpDownloadHistoryFactory.create()).thenReturn(kpdh1)
            .thenReturn(kpdh2);
         
        kpService.acquire();
        
        verify(dateTimeProvider).getNow();
        verify(dateTimeHelper).truncateToDays(now);
        verify(kpReaderRepository, times(2)).read();
        verify(kpl1).get(0);
        verify(kpl2).get(0);
        verify(dateTimeHelper).truncateToDays(period1);
        verify(dateTimeHelper).truncateToDays(period2);
        verify(kpdh1).setComplete(true);
        verify(kpdh2).setComplete(false);
        verify(kpdh1).setPeriod(truncatedPeriod1); 
        verify(kpdh2).setPeriod(truncatedNow);
        verify(kpDownloadHistoryFactory, times(2)).create();
        verify(kpDownloadHistoryWriterRepository).save(kpdh1);
        verify(kpDownloadHistoryWriterRepository).save(kpdh2);
        verify(kpWriterRepository).save(kpl1);
        verify(kpWriterRepository).save(kpl2);
    }
    
}
