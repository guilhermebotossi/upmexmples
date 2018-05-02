package br.inpe.climaespacial.swd.commons.adapters;

import java.io.IOException;

public interface DownloadAdapter {

	String download(String url) throws IOException;
	
}