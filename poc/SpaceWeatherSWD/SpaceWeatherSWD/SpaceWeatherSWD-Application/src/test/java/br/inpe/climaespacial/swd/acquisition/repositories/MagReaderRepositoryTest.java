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

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.mappers.MagMapper;
import br.inpe.climaespacial.swd.acquisition.providers.AquisitionURLProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.commons.services.Downloader;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultMagReaderRepository.class)
public class MagReaderRepositoryTest {

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
	private MagMapper magMapper;

	@Inject
	private MagReaderRepository magReaderRepository;

	@Test
	public void listLast_called_returnsList() {
		String filename = "5-minute.json";
		when(filenameProvider.getFilename()).thenReturn(filename);
		String url = "http://host/";
		when(aquisitionURLProvider.getURL()).thenReturn(url);
		String downloadURL = url + "mag-" + filename;
		String content = "content";
		when(downloader.download(downloadURL)).thenReturn(content);
		List<Mag> expectedMdl = mockList(Mag.class);
		when(magMapper.map(content)).thenReturn(expectedMdl);

		List<Mag> mdl = magReaderRepository.listLast();

		verify(filenameProvider, times(1)).getFilename();
		verify(aquisitionURLProvider, times(1)).getURL();
		verify(downloader, times(1)).download(refEq(downloadURL));
		verify(magMapper, times(1)).map(refEq(content));
		assertSame(expectedMdl, mdl);
	}
}
