package br.inpe.climaespacial.swd.acquisition.services;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
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
import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.repositories.MagReaderRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.MagWriterRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.PlasmaReaderRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.PlasmaWriterRepository;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultDataAquisition.class)
public class DataAquisitionTest {

	@Produces
	@Mock
	private MagReaderRepository magReaderRepository;

	@Produces
	@Mock
	private PlasmaReaderRepository plasmaReaderRepository;

	@Produces
	@Mock
	private MagWriterRepository magWriterRepository;

	@Produces
	@Mock
	private PlasmaWriterRepository plasmaWriterRepository;

	@Inject
	private DataAquisition dataAquisition;

	@Test
	public void acquire_called_succeeds() {
		List<Mag> ml = mockList(Mag.class);
		List<Plasma> pl = mockList(Plasma.class);
		when(magReaderRepository.listLast()).thenReturn(ml);
		when(plasmaReaderRepository.listLast()).thenReturn(pl);

		dataAquisition.acquire();

		verify(magReaderRepository, times(1)).listLast();
		verify(plasmaReaderRepository, times(1)).listLast();
		verify(magWriterRepository, times(1)).save(refEq(ml));
		verify(plasmaWriterRepository, times(1)).save(refEq(pl));
	}
}
