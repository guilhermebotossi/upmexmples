package br.inpe.climaespacial.swd.acquisition.repositories;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.mappers.MagMapper;
import br.inpe.climaespacial.swd.acquisition.providers.AquisitionURLProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.commons.services.Downloader;

@Dependent
public class DefaultMagReaderRepository implements MagReaderRepository {

	@Inject
	private FilenameProvider filenameProvider;

	@Inject
	private AquisitionURLProvider aquisitionURLProvider;

	@Inject
	private Downloader downloader;

	@Inject
	private MagMapper magMapper;

	@Override
	public List<Mag> listLast() {
		String filename = filenameProvider.getFilename();
		String url = aquisitionURLProvider.getURL();
		String downloadURL = createURL(url, filename);
		String content = downloader.download(downloadURL);
		List<Mag> mdl = magMapper.map(content);
		return mdl;
	}

	private String createURL(String url, String filename) {
		return url + "mag-" + filename;
	}
}
