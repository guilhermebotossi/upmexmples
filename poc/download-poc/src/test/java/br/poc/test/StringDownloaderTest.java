package br.poc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
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

import br.poc.adapter.DownloadAdapter;
import br.poc.downloader.Downloader;

@RunWith(CdiRunner.class)
@AdditionalClasses(StringDownloader.class)
public class StringDownloaderTest {
	
	@Produces @Mock
	DownloadAdapter da;
	
	@Inject
	Downloader d;
	
	@Test
	public void download_whenfail_throws() throws IOException {
		IOException expectedre = new IOException();
		
		doThrow(expectedre).when(da).download(any(String.class));
		
		RuntimeException re = null;
		try {
			d.download("teste");
		} catch (RuntimeException e) {
			re = e;
		}
		assertNotNull(re);
		assertEquals("Erro ao efetuar download.", re.getMessage());
	}
	
	@Test
	public void download_calledwithvalidurl_succeeds() throws IOException {
		String value = "teste";
		
		when(da.download(any(String.class))).thenReturn(value);
		
		String actual = d.download(value);

		assertNotNull(actual);
		assertEquals(value, actual);
	}
}
