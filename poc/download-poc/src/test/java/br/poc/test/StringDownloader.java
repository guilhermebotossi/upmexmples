package br.poc.test;

import java.io.IOException;

import javax.inject.Inject;

public class StringDownloader implements Downloader {

	private DownloadAdapter da;

	@Inject
	public StringDownloader(DownloadAdapter da) {
		this.da = da;
	}

	public String download(String url) {
		try {
			return da.download(url);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao efetuar download.");
		}		
	}

}
