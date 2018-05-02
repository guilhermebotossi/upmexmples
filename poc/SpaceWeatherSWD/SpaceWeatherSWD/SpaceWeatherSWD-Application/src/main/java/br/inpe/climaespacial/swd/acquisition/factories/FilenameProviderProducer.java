package br.inpe.climaespacial.swd.acquisition.factories;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.acquisition.qualifiers.MagLastRecordQualifier;
import br.inpe.climaespacial.swd.acquisition.qualifiers.PlasmaLastRecordQualifier;
import br.inpe.climaespacial.swd.acquisition.repositories.DefaultMagReaderRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.DefaultPlasmaReaderRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.LastRecordRepository;

@Dependent
public class FilenameProviderProducer {

	private MagLastRecordQualifierAnnotationFactory magLastRecordQualifierAnnotationFactory;

	private PlasmaLastRecordQualifierAnnotationFactory plasmaLastRecordQualifierAnnotationFactory;

	private Instance<LastRecordRepository> lastRecordRepositoryInstance;

	private Instance<DateTimeProvider> dateTimeProviderInstance;

	private FilenameProviderFactory filenameProviderFactory;

	@Inject
	public FilenameProviderProducer(MagLastRecordQualifierAnnotationFactory magLastRecordQualifierAnnotationFactory,
			PlasmaLastRecordQualifierAnnotationFactory plasmaLastRecordQualifierAnnotationFactory,
			@Any Instance<LastRecordRepository> lastRecordRepositoryInstance,
			@Any Instance<DateTimeProvider> dateTimeProviderInstance, FilenameProviderFactory filenameProviderFactory) {
		this.magLastRecordQualifierAnnotationFactory = magLastRecordQualifierAnnotationFactory;
		this.plasmaLastRecordQualifierAnnotationFactory = plasmaLastRecordQualifierAnnotationFactory;
		this.lastRecordRepositoryInstance = lastRecordRepositoryInstance;
		this.dateTimeProviderInstance = dateTimeProviderInstance;
		this.filenameProviderFactory = filenameProviderFactory;
	}

	@Produces
	public FilenameProvider create(InjectionPoint injectionPoint) {
		LastRecordRepository lrr;
		Class<?> dc = injectionPoint.getMember().getDeclaringClass();
		if (dc == DefaultMagReaderRepository.class) {
			lrr = createWithMagLastRecordRepository();
		} else if (dc == DefaultPlasmaReaderRepository.class) {
			lrr = createWithPlasmaLastRecordRepository();
		} else {
			lrr = createWithMagLastRecordRepository();
		}
		return filenameProviderFactory.create(lrr, dateTimeProviderInstance.get());
	}

	private LastRecordRepository createWithMagLastRecordRepository() {
		AnnotationLiteral<MagLastRecordQualifier> mlrqal = magLastRecordQualifierAnnotationFactory.create();
		LastRecordRepository lrr = lastRecordRepositoryInstance.select(mlrqal).get();
		return lrr;
	}

	private LastRecordRepository createWithPlasmaLastRecordRepository() {
		AnnotationLiteral<PlasmaLastRecordQualifier> mlrqal = plasmaLastRecordQualifierAnnotationFactory.create();
		LastRecordRepository lrr = lastRecordRepositoryInstance.select(mlrqal).get();
		return lrr;
	}

}
