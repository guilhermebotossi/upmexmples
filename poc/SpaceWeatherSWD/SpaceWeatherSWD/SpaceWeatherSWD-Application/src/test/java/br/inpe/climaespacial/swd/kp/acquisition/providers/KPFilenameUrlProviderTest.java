package br.inpe.climaespacial.swd.kp.acquisition.providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.KPDownloadHistoryReaderRepository;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPFilenameUrlProvider.class)
public class KPFilenameUrlProviderTest {

    @Mock
    @Produces
    private DateTimeProvider dateTimeProvider;

    @Mock
    @Produces
    private KPDownloadHistoryReaderRepository kpLastRecordRepository;

    @Inject
    private KPFilenameUrlProvider kpFilenameUrlProvider;

    @Test
    public void getFilenameUrl_calledAndKPLastRecordRepositoryReturnsNull_returnsFirstKSAFile() {
        String firstUrl = "ftp://ftp.gfz-potsdam.de/pub/home/obs/kp-ap/tab/kp1102.tab";
        when(kpLastRecordRepository.getNextDateToBeDownloaded()).thenReturn(null);

        String filenameUrl = kpFilenameUrlProvider.getFilenameUrl();

        verify(kpLastRecordRepository).getNextDateToBeDownloaded();
        assertNotNull(filenameUrl);
        assertEquals(firstUrl, filenameUrl);
    }
 
    @Test
    public void getFilenameUrl_calledAndKPLastRecordRepositoryReturnMonthPlusOneBeforeCurrentMonth_returnsLastMonthPlusOneKSAFile() {
        String lastMonthPlusOneKSAUrl = "ftp://ftp.gfz-potsdam.de/pub/home/obs/kp-ap/tab/kp1605.tab";
        ZonedDateTime currentDate = ZonedDateTime.parse("2017-06-01T00:00:00z[UTC]");
        ZonedDateTime lastDate = ZonedDateTime.parse("2016-04-30T23:00:00z[UTC]");
        when(kpLastRecordRepository.getNextDateToBeDownloaded()).thenReturn(lastDate);
        when(dateTimeProvider.getNow()).thenReturn(currentDate);

        String filenameUrl = kpFilenameUrlProvider.getFilenameUrl();

        verify(kpLastRecordRepository).getNextDateToBeDownloaded();
        verify(dateTimeProvider).getNow();
        assertNotNull(filenameUrl);
        assertEquals(lastMonthPlusOneKSAUrl, filenameUrl);
    }

    @Test
    public void getFilenameUrl_calledAndKPLastRecordRepositoryReturnMonthPlusOneBeforeCurrentMonth_returnsLastMonthPlusOneKSAFile2() {
        String lastMonthPlusOneKSAUrl = "ftp://ftp.gfz-potsdam.de/pub/home/obs/kp-ap/tab/kp1702.tab";
        ZonedDateTime currentDate = ZonedDateTime.parse("2017-06-01T00:00:00z[UTC]");
        ZonedDateTime lastDate = ZonedDateTime.parse("2017-01-30T23:00:00z[UTC]");
        when(kpLastRecordRepository.getNextDateToBeDownloaded()).thenReturn(lastDate);
        when(dateTimeProvider.getNow()).thenReturn(currentDate);

        String filenameUrl = kpFilenameUrlProvider.getFilenameUrl();

        verify(kpLastRecordRepository).getNextDateToBeDownloaded();
        verify(dateTimeProvider).getNow();
        assertNotNull(filenameUrl);
        assertEquals(lastMonthPlusOneKSAUrl, filenameUrl);
    }
    @Test
    public void getFilenameUrl_calledAndKPLastRecordRepositoryReturnMonthPlusOneEqualToCurrentMonth_returnsRealTimeKSAFile() {
        String realTimeKSAUrl = "http://www-app3.gfz-potsdam.de/kp_index/qlyymm.tab";
        ZonedDateTime currentDate = ZonedDateTime.parse("2017-05-01T00:00:00z[UTC]");
        ZonedDateTime lastDate = ZonedDateTime.parse("2017-04-30T23:00:00z[UTC]");
        when(kpLastRecordRepository.getNextDateToBeDownloaded()).thenReturn(lastDate);
        when(dateTimeProvider.getNow()).thenReturn(currentDate);

        String filenameUrl = kpFilenameUrlProvider.getFilenameUrl();

        verify(kpLastRecordRepository).getNextDateToBeDownloaded();
        verify(dateTimeProvider).getNow();
        assertNotNull(filenameUrl);
        assertEquals(realTimeKSAUrl, filenameUrl);
    }

    @Test
    public void getFilenameUrl_calledAndKPLastRecordRepositoryReturnMonthPlusOneAfterToCurrentMonth_throws() {        
        ZonedDateTime currentDate = ZonedDateTime.parse("2017-05-01T00:00:00z[UTC]");
        ZonedDateTime lastDate = ZonedDateTime.parse("2017-05-01T00:00:00z[UTC]");
        when(kpLastRecordRepository.getNextDateToBeDownloaded()).thenReturn(lastDate);
        when(dateTimeProvider.getNow()).thenReturn(currentDate);

        RuntimeException re = null;

        try {
            kpFilenameUrlProvider.getFilenameUrl();
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Invalid URL", re.getMessage());
    }

    @Test
    public void getKPRealtimeUrl_called_succeeds() { 
    	String realTimeKPUrl = "http://www-app3.gfz-potsdam.de/kp_index/qlyymm.tab";
    	
    	String url = kpFilenameUrlProvider.getKPRealtimeUrl(); 
    	
    	assertNotNull(url);
    	assertEquals(realTimeKPUrl, url);
    }
}
