package br.inpe.climaespacial.swd.commons.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.commons.adapters.DownloadAdapter;
import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;
import br.inpe.climaespacial.swd.commons.exceptions.DownloadException;

@RunWith(CdiRunner.class)
@AdditionalClasses(StringDownloader.class)
public class StringDownloaderTest {

    @Produces
    @Mock
    private DownloadAdapter downloadAdapter;

    @Inject
    private Downloader download;

    @Test
    public void download_calledWithNullArgument_throws() {
        ArgumentException ae = null;
        try {
            download.download(null);
        } catch (ArgumentException e) {
            ae = e;
        }
        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals("Argument \"url\" cannot be null.", ae.getMessage());
    }

    @Test
    public void download_whenfails_throws() throws IOException {
        IOException expectedre = new IOException();

        doThrow(expectedre).when(downloadAdapter).download(any(String.class));

        DownloadException de = null;
        try {
            download.download("teste");
        } catch (DownloadException e) {
            de = e;
        }
        assertNotNull(de);
        assertEquals(DownloadException.class, de.getClass());
        assertEquals("Erro ao efetuar download.", de.getMessage());
    }

    @Test
    public void download_calledwithvalidurl_succeeds() throws IOException {
        String value = "teste";

        when(downloadAdapter.download(any(String.class))).thenReturn(value);

        String actual = download.download(value);

        assertNotNull(actual);
        assertEquals(value, actual);
    }

}
