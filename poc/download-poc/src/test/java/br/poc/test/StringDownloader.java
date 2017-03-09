package br.poc.test;

import java.io.IOException;

import javax.inject.Inject;

import br.poc.adapter.DownloadAdapter;
import br.poc.downloader.Downloader;

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
