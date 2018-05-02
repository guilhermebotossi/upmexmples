package br.inpe.climaespacial.swd.commons.adapters;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.interceptors.DefaultTransientFaultInterceptor;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    StringDownloadAdapter.class,
    DefaultTransientFaultInterceptor.class
})
public class StringDownloaderAdapterIntegrationTest {

    @Inject
    private StringDownloadAdapter stringDownloadAdapter;

    @Test
    public void download_called_returnsFile() throws IOException {
        String url = "http://services.swpc.noaa.gov/products/solar-wind/mag-5-minute.json";

        String returnedString = stringDownloadAdapter.download(url);

        assertThat(returnedString, containsString("[\"time_tag\",\"bx_gsm\",\"by_gsm\",\"bz_gsm\",\"lon_gsm\",\"lat_gsm\",\"bt\"]"));
    }

}
