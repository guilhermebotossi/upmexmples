package br.inpe.climaespacial.swd.commons.adapters;

import br.inpe.climaespacial.swd.commons.interceptors.TransientFaultInterceptor;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import javax.enterprise.context.Dependent;
import org.apache.commons.io.IOUtils;

@Dependent
public class StringDownloadAdapter implements DownloadAdapter {

    @Override
    @TransientFaultInterceptor
    public String download(String url) throws IOException {
        return IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
    }
}