package br.poc.adapter;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class StringDownloaderAdapter implements DownloadAdapter {

	@Override
	public String download(String url) throws IOException {
		return IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
	}

}
