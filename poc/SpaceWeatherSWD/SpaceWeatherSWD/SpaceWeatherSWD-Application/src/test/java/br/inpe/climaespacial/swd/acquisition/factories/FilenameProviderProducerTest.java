package br.inpe.climaespacial.swd.acquisition.factories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Member;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.AnnotationLiteral;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.acquisition.qualifiers.MagLastRecordQualifier;
import br.inpe.climaespacial.swd.acquisition.qualifiers.PlasmaLastRecordQualifier;
import br.inpe.climaespacial.swd.acquisition.repositories.DefaultMagReaderRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.DefaultPlasmaReaderRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.LastRecordRepository;
import br.inpe.climaespacial.swd.commons.EmbraceMockito;

@RunWith(CdiRunner.class)
public class FilenameProviderProducerTest {

	private DateTimeProvider dateTimeProvider;

	private LastRecordRepository lastRecordRepository;

	private MagLastRecordQualifierAnnotationFactory magLastRecordQualifierAnnotationFactory;

	private PlasmaLastRecordQualifierAnnotationFactory plasmaLastRecordQualifierAnnotationFactory;

	private Instance<LastRecordRepository> lastRecordRepositoryInstance;

	private FilenameProviderFactory filenameProviderFactory;

	private Instance<DateTimeProvider> dateTimeProviderInstance;

	private FilenameProviderProducer filenameProviderProducer;

	@Before
	public void before() {
		dateTimeProvider = mock(DateTimeProvider.class);

		lastRecordRepository = mock(LastRecordRepository.class);

		magLastRecordQualifierAnnotationFactory = mock(MagLastRecordQualifierAnnotationFactory.class);

		plasmaLastRecordQualifierAnnotationFactory = mock(PlasmaLastRecordQualifierAnnotationFactory.class);

		lastRecordRepositoryInstance = EmbraceMockito.mockIntance(LastRecordRepository.class);

		filenameProviderFactory = mock(FilenameProviderFactory.class);

		dateTimeProviderInstance = EmbraceMockito.mockIntance(DateTimeProvider.class);

		filenameProviderProducer = new FilenameProviderProducer(magLastRecordQualifierAnnotationFactory,
				plasmaLastRecordQualifierAnnotationFactory, lastRecordRepositoryInstance, dateTimeProviderInstance,
				filenameProviderFactory);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void create_called_returnsFileNameProviderWithMagLastRecordRepository() {
		FilenameProvider fp1 = mock(FilenameProvider.class);
		when(filenameProviderFactory.create(lastRecordRepository, dateTimeProvider)).thenReturn(fp1);
		Instance<LastRecordRepository> lrri = mock(Instance.class);
		when(lrri.get()).thenReturn(lastRecordRepository);
		@SuppressWarnings("serial")
		AnnotationLiteral<MagLastRecordQualifier> mlrqal = new AnnotationLiteral<MagLastRecordQualifier>() {
		};
		when(magLastRecordQualifierAnnotationFactory.create()).thenReturn(mlrqal);
		when(lastRecordRepositoryInstance.select(mlrqal)).thenReturn(lrri);
		when(dateTimeProviderInstance.get()).thenReturn(dateTimeProvider);
		Member m = mock(Member.class);
		doReturn(DefaultMagReaderRepository.class).when(m).getDeclaringClass();
		InjectionPoint ip = mock(InjectionPoint.class);
		when(ip.getMember()).thenReturn(m);

		FilenameProvider fp2 = filenameProviderProducer.create(ip);

		assertNotNull(fp2);
		assertSame(fp1, fp2);
		verify(ip, times(1)).getMember();
		verify(m, times(1)).getDeclaringClass();
		verify(magLastRecordQualifierAnnotationFactory, times(1)).create();
		verify(plasmaLastRecordQualifierAnnotationFactory, never()).create();
		verify(lastRecordRepositoryInstance, times(1)).select(eq(mlrqal));
		verify(filenameProviderFactory, times(1)).create(lastRecordRepository, dateTimeProvider);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void create_called_returnsFileNameProviderWithPlasmaLastRecordRepository() {
		FilenameProvider fp1 = mock(FilenameProvider.class);
		when(filenameProviderFactory.create(lastRecordRepository, dateTimeProvider)).thenReturn(fp1);
		Instance<LastRecordRepository> lrri = mock(Instance.class);
		when(lrri.get()).thenReturn(lastRecordRepository);
		@SuppressWarnings("serial")
		AnnotationLiteral<PlasmaLastRecordQualifier> plrqal = new AnnotationLiteral<PlasmaLastRecordQualifier>() {
		};
		when(plasmaLastRecordQualifierAnnotationFactory.create()).thenReturn(plrqal);
		when(lastRecordRepositoryInstance.select(plrqal)).thenReturn(lrri);
		when(dateTimeProviderInstance.get()).thenReturn(dateTimeProvider);
		Member m = mock(Member.class);
		doReturn(DefaultPlasmaReaderRepository.class).when(m).getDeclaringClass();
		InjectionPoint ip = mock(InjectionPoint.class);
		when(ip.getMember()).thenReturn(m);

		FilenameProvider fp2 = filenameProviderProducer.create(ip);

		assertNotNull(fp2);
		assertSame(fp1, fp2);
		verify(ip, times(1)).getMember();
		verify(m, times(1)).getDeclaringClass();
		verify(magLastRecordQualifierAnnotationFactory, never()).create();
		verify(plasmaLastRecordQualifierAnnotationFactory, times(1)).create();
		verify(lastRecordRepositoryInstance, times(1)).select(eq(plrqal));
		verify(filenameProviderFactory, times(1)).create(lastRecordRepository, dateTimeProvider);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void create_calledWhenInjectedInAnyObject_returnsFileNameProviderWithMagLastRecordRepository() {
		FilenameProvider fp1 = mock(FilenameProvider.class);
		when(filenameProviderFactory.create(lastRecordRepository, dateTimeProvider)).thenReturn(fp1);
		Instance<LastRecordRepository> lrri = mock(Instance.class);
		when(lrri.get()).thenReturn(lastRecordRepository);
		@SuppressWarnings("serial")
		AnnotationLiteral<MagLastRecordQualifier> mlrqal = new AnnotationLiteral<MagLastRecordQualifier>() {
		};
		when(magLastRecordQualifierAnnotationFactory.create()).thenReturn(mlrqal);
		when(lastRecordRepositoryInstance.select(mlrqal)).thenReturn(lrri);
		when(dateTimeProviderInstance.get()).thenReturn(dateTimeProvider);
		Member m = mock(Member.class);
		doReturn(Object.class).when(m).getDeclaringClass();
		InjectionPoint ip = mock(InjectionPoint.class);
		when(ip.getMember()).thenReturn(m);

		FilenameProvider fp2 = filenameProviderProducer.create(ip);

		assertNotNull(fp2);
		assertSame(fp1, fp2);
		verify(ip, times(1)).getMember();
		verify(m, times(1)).getDeclaringClass();
		verify(magLastRecordQualifierAnnotationFactory, times(1)).create();
		verify(plasmaLastRecordQualifierAnnotationFactory, never()).create();
		verify(lastRecordRepositoryInstance, times(1)).select(eq(mlrqal));
		verify(filenameProviderFactory, times(1)).create(lastRecordRepository, dateTimeProvider);
	}

}
