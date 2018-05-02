package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.acquisition.providers.FilenameProvider;
import br.inpe.climaespacial.swd.acquisition.repositories.DefaultMagLastRecordRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.DefaultMagReaderRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.DefaultPlasmaLastRecordRepository;
import br.inpe.climaespacial.swd.acquisition.repositories.DefaultPlasmaReaderRepository;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.lang.reflect.Member;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    FilenameProviderProducer.class,
    DefaultDateTimeProvider.class,
    DefaultFilenameProviderFactory.class,
    DefaultMagLastRecordRepository.class,
    DefaultPlasmaLastRecordRepository.class,
    DefaultPlasmaLastRecordQualifierAnnotationFactory.class,
    DefaultMagLastRecordQualifierAnnotationFactory.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class FilenameProviderProducerIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private FilenameProviderProducer filenameProviderProducer;

    @InRequestScope
    @Test
    public void create_calledFromMag_returnsFilenameProviderForMag() {
        MagEntity me = createMagEntity();
        me.setTimeTag(ZonedDateTime.now());
        entityManager.persist(me);
        Member m = mock(Member.class);
        doReturn(DefaultMagReaderRepository.class).when(m).getDeclaringClass();
        InjectionPoint ip = mock(InjectionPoint.class);
        when(ip.getMember()).thenReturn(m);

        FilenameProvider fp = filenameProviderProducer.create(ip);

        assertEquals("5-minute.json", fp.getFilename());
    }

    @InRequestScope
    @Test
    public void create_calledFromPlasma_returnsFilenameProviderForPlasma() {
        PlasmaEntity me = createPlasmaEntity();
        me.setTimeTag(ZonedDateTime.now().minusMinutes(5));
        entityManager.persist(me);
        Member m = mock(Member.class);
        doReturn(DefaultPlasmaReaderRepository.class).when(m).getDeclaringClass();
        InjectionPoint ip = mock(InjectionPoint.class);
        when(ip.getMember()).thenReturn(m);

        FilenameProvider fp = filenameProviderProducer.create(ip);

        assertEquals("2-hour.json", fp.getFilename());
    }

    private MagEntity createMagEntity() {
        MagEntity me = new MagEntity();
        me.setId(UUID.randomUUID());
        me.setCreationDate(ZonedDateTime.now());
        me.setModificationDate(ZonedDateTime.now());
        me.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        me.setBt(0D);
        me.setBxGsm(0D);
        me.setByGsm(0D);
        me.setBzGsm(0D);
        me.setLatGsm(0D);
        me.setLonGsm(0D);
        return me;
    }

    private PlasmaEntity createPlasmaEntity() {
        PlasmaEntity me = new PlasmaEntity();
        me.setId(UUID.randomUUID());
        me.setCreationDate(ZonedDateTime.now());
        me.setModificationDate(ZonedDateTime.now());
        me.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        me.setDensity(0D);
        me.setSpeed(0D);
        me.setTemperature(0D);
        return me;
    }
}
