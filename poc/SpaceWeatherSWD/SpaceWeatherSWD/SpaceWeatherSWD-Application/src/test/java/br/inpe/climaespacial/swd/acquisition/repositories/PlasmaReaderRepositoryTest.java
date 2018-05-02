package br.inpe.climaespacial.swd.acquisition.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.mappers.PlasmaMapper;
import br.inpe.climaespacial.swd.acquisition.providers.AquisitionURLProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.commons.services.Downloader;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultPlasmaReaderRepository.class)
public class PlasmaReaderRepositoryTest {

	@Produces
	@Mock
	private FilenameProvider filenameProvider;

	@Produces
	@Mock
	private AquisitionURLProvider aquisitionURLProvider;

	@Produces
	@Mock
	private Downloader downloader;

	@Produces
	@Mock
	private PlasmaMapper plasmaMapper;

	@Inject
	private PlasmaReaderRepository plasmaReaderRepository;

	@Test
	public void listLast_called_returnsList() {

		String filename = "5-minute.json";
		when(filenameProvider.getFilename()).thenReturn(filename);

		String url = "http://host/";
		when(aquisitionURLProvider.getURL()).thenReturn(url);

		String downloadURL = url + "plasma-" + filename;
		String content = "content";
		when(downloader.download(downloadURL)).thenReturn(content);

		List<Plasma> expectedPdl = mockList(Plasma.class);
		when(plasmaMapper.map(content)).thenReturn(expectedPdl);

		List<Plasma> pdl = plasmaReaderRepository.listLast();

		verify(filenameProvider, times(1)).getFilename();
		verify(aquisitionURLProvider, times(1)).getURL();
		verify(downloader, times(1)).download(refEq(downloadURL));
		verify(plasmaMapper, times(1)).map(refEq(content));
		assertSame(expectedPdl, pdl);
	}
}
