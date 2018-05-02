package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
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
import br.inpe.climaespacial.swd.commons.services.Downloader;
import br.inpe.climaespacial.swd.kp.acquisition.providers.KPFilenameUrlProvider;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.mappers.KPMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPReaderRepository.class)
public class KPReaderRepositoryTest {

    @Mock
    @Produces
    private KPMapper kpMapper;
    
    @Mock
    @Produces
    private KPFilenameUrlProvider kpFilenameUrlProvider;
    
    @Mock
    @Produces
    private DateTimeProvider dateTimeProvider;
    
    @Mock
    @Produces
    private Downloader downloader;
    
    @Inject
    private KPReaderRepository kpReaderRepository;
    
    @Test 
    public void read_calledWithRealTimeFileButWithHourNotDivisibleBy3_ignoresLastLine() {
        String filenameUrl = "realTimeurl";        
        when(kpFilenameUrlProvider.getFilenameUrl()).thenReturn(filenameUrl);
        when(kpFilenameUrlProvider.getKPRealtimeUrl()).thenReturn(filenameUrl);
        String content = "content1\ncontent2";
        String contentWithoutLastLine = "content1";
        when(downloader.download(filenameUrl)).thenReturn(content);
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T11:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(zdt);
        List<KP> kpl1 = mockList(KP.class);
        when(kpMapper.map(contentWithoutLastLine)).thenReturn(kpl1);
        
        List<KP> kpl2  = kpReaderRepository.read(); 
        
        assertNotNull(kpl2);
        assertSame(kpl1, kpl2);
        verify(kpFilenameUrlProvider).getFilenameUrl(); 
        verify(kpFilenameUrlProvider).getKPRealtimeUrl(); 
        verify(dateTimeProvider).getNow();
        verify(downloader).download(filenameUrl);
        verify(kpMapper).map(contentWithoutLastLine); 
    }
    
    @Test 
    public void read_calledWithRealTimeFileButWithHourNotDivisibleBy3_ignoresLastLine2() {
        String filenameUrl = "realTimeurl";        
        when(kpFilenameUrlProvider.getFilenameUrl()).thenReturn(filenameUrl);
        when(kpFilenameUrlProvider.getKPRealtimeUrl()).thenReturn(filenameUrl);
        String content = "content1\ncontent2\n";
        String contentWithoutLastLine = "content1";
        when(downloader.download(filenameUrl)).thenReturn(content);
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T11:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(zdt);
        List<KP> kpl1 = mockList(KP.class);
        when(kpMapper.map(contentWithoutLastLine)).thenReturn(kpl1);
        
        List<KP> kpl2  = kpReaderRepository.read(); 
        
        assertNotNull(kpl2);
        assertSame(kpl1, kpl2);
        verify(kpFilenameUrlProvider).getFilenameUrl(); 
        verify(kpFilenameUrlProvider).getKPRealtimeUrl(); 
        verify(dateTimeProvider).getNow();
        verify(downloader).download(filenameUrl);
        verify(kpMapper).map(contentWithoutLastLine); 
    }
    
    @Test 
    public void read_calledWithRealTimeFileButWithHourDivisibleBy3_downloadsFully() {
        String filenameUrl = "realTimeurl";        
        when(kpFilenameUrlProvider.getFilenameUrl()).thenReturn(filenameUrl);
        when(kpFilenameUrlProvider.getKPRealtimeUrl()).thenReturn(filenameUrl);
        String content = "content1\ncontent2";
        when(downloader.download(filenameUrl)).thenReturn(content);
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(zdt);
        List<KP> kpl1 = mockList(KP.class);
        when(kpMapper.map(content)).thenReturn(kpl1);
        
        List<KP> kpl2  = kpReaderRepository.read(); 
        
        assertNotNull(kpl2);
        assertSame(kpl1, kpl2);
        verify(kpFilenameUrlProvider).getFilenameUrl(); 
        verify(kpFilenameUrlProvider).getKPRealtimeUrl(); 
        verify(dateTimeProvider).getNow();
        verify(downloader).download(filenameUrl);
        verify(kpMapper).map(content); 
    }
    

    @Test
    public void read_called_succeeds() {
        String filenameUrl = "url";        
        String realTimeUrl = "realTimeurl";  
        when(kpFilenameUrlProvider.getFilenameUrl()).thenReturn(filenameUrl);
        when(kpFilenameUrlProvider.getKPRealtimeUrl()).thenReturn(realTimeUrl);
        String content = "content1\ncontent2";
        when(downloader.download(filenameUrl)).thenReturn(content);
        List<KP> kpl1 = mockList(KP.class);
        when(kpMapper.map(content)).thenReturn(kpl1);
        
        List<KP> kpl2  = kpReaderRepository.read(); 
        
        assertNotNull(kpl2);
        assertSame(kpl1, kpl2);
        verify(kpFilenameUrlProvider).getFilenameUrl(); 
        verify(downloader).download(filenameUrl);
        verify(kpMapper).map(content);
    }


}
