package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.services.Downloader;
import br.inpe.climaespacial.swd.kp.acquisition.providers.KPFilenameUrlProvider;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.mappers.KPMapper;

@Dependent
public class DefaultKPReaderRepository implements KPReaderRepository {

	@Inject
	private KPMapper kpMapper;

	@Inject
	private KPFilenameUrlProvider kpFilenameUrlProvider;

	@Inject
	private Downloader downloader;

	@Inject
	private DateTimeProvider dateTimeProvider;

	@Override
	public List<KP> read() {
		String filenameUrl = kpFilenameUrlProvider.getFilenameUrl();
		String realTimeUrl = kpFilenameUrlProvider.getKPRealtimeUrl();

		String content = downloader.download(filenameUrl);

		if (realTimeUrl.equals(filenameUrl)) {
			ZonedDateTime zdt = dateTimeProvider.getNow();
			int hour = zdt.getHour();
			if (hour % 3 > 0) {
				int lastIndexOf = content.lastIndexOf("\n", content.length() - 2);
				content = content.substring(0, lastIndexOf);
			}
		}

		return kpMapper.map(content);
	}

}
