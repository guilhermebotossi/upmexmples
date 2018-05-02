package br.inpe.climaespacial.swd.acquisition.repositories;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.mappers.PlasmaMapper;
import br.inpe.climaespacial.swd.acquisition.providers.AquisitionURLProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.commons.services.Downloader;

@Dependent
public class DefaultPlasmaReaderRepository implements PlasmaReaderRepository {

	@Inject
	private FilenameProvider filenameProvider;

	@Inject
	private AquisitionURLProvider aquisitionURLProvider;

	@Inject
	private Downloader downloader;

	@Inject
	private PlasmaMapper plasmaMapper;

	@Override
	public List<Plasma> listLast() {
		String filename = filenameProvider.getFilename();
		String url = aquisitionURLProvider.getURL();
		String downloadURL = createURL(url, filename);
		String content = downloader.download(downloadURL);
		List<Plasma> pdl = plasmaMapper.map(content);
		return pdl;
	}

	private static String createURL(String url, String filename) {
		return url + "plasma-" + filename;
	}
}
