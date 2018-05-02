package br.inpe.climaespacial.swd.kp.values.repositories;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.adapters.StringDownloadAdapter;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.services.StringDownloader;
import br.inpe.climaespacial.swd.commons.utils.DefaultCollectionUtils;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.acquisition.providers.DefaultKPFilenameUrlProvider;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.DefaultKPDownloadHistoryReaderRepository;
import br.inpe.climaespacial.swd.kp.acquisition.repositories.DefaultKPReaderRepository;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.factories.DefaultKPFactory;
import br.inpe.climaespacial.swd.kp.factories.DefaultKPValueFactory;
import br.inpe.climaespacial.swd.kp.forecast.mappers.DefaultKPValueMapper;
import br.inpe.climaespacial.swd.kp.mappers.DefaultKPMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    HelperFactoryProducer.class,
    DefaultKPReaderRepository.class,
    DefaultKPMapper.class,
    DefaultListFactory.class,
    DefaultKPFactory.class,
    DefaultCollectionUtils.class,
    DefaultKPValueFactory.class,
    DefaultKPValueReaderRepository.class,
    DefaultKPValueMapper.class,
    StringDownloader.class,
    DefaultKPFilenameUrlProvider.class,
    DefaultDateTimeProvider.class,
    StringDownloadAdapter.class,
    DefaultKPDownloadHistoryReaderRepository.class
    
    
})
public class KPValueReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private KPValueReaderRepository kpValueReaderRepository;

    @Test
    @InRequestScope
    public void getLastKPIndexes_called_returnsEmptyList() {
        List<KPValue> lastKPIndexes = kpValueReaderRepository.getLastKPIndexes();

        assertNotNull(lastKPIndexes);
        assertThat(lastKPIndexes, is(empty()));
    }

    @Test
    @InRequestScope
    public void getLastKPIndexes_called_returnsList() {
        KPEntity kpe1 = createKPentity(ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]"));
        persist(kpe1);

        KPEntity kpe2 = createKPentity(ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]"));
        persist(kpe2);
        
        KPEntity kpe3 = createKPentity(ZonedDateTime.parse("2017-01-03T00:00:00z[UTC]"));
        persist(kpe3);
        
        KPEntity kpe4 = createKPentity(ZonedDateTime.parse("2017-01-04T00:00:00z[UTC]"));
        persist(kpe4);
        
        KPEntity kpe5 = createKPentity(ZonedDateTime.parse("2017-01-05T00:00:00z[UTC]"));
        persist(kpe5);

        List<KPValue> lastKPIndexes = kpValueReaderRepository.getLastKPIndexes();

        assertNotNull(lastKPIndexes);
        assertThat(lastKPIndexes, is(not(empty())));
        assertThat(lastKPIndexes, hasSize(4));

        KPValue kpvf1 = lastKPIndexes.get(0);
        assertNotNull(kpvf1);
        assertEquals(kpe1.getTimeTag(), kpvf1.getTimeTag());

        KPValue kpvf2 = lastKPIndexes.get(1);
        assertNotNull(kpvf2);
        assertEquals(kpe2.getTimeTag(), kpvf2.getTimeTag());
        
        KPValue kpvf3 = lastKPIndexes.get(2);
        assertNotNull(kpvf3);
        assertEquals(kpe3.getTimeTag(), kpvf3.getTimeTag());

        KPValue kpvf4 = lastKPIndexes.get(3);
        assertNotNull(kpvf4);
        assertEquals(kpe4.getTimeTag(), kpvf4.getTimeTag());
    }

    private void persist(KPEntity kpEntity) {
        entityManager.persist(kpEntity);
        kpEntity.getKPValueList().forEach(kpv -> {entityManager.persist(kpv);});
    }

    private KPEntity createKPentity(ZonedDateTime timeTag) {
        KPEntity kpe = new KPEntity();
        kpe.setId(UUID.randomUUID());
        kpe.setCreationDate(ZonedDateTime.now());
        kpe.setModificationDate(ZonedDateTime.now());
        kpe.setTimeTag(timeTag);
        
        KPValueEntity kpve = new KPValueEntity();
        kpve.setId(UUID.randomUUID());
        kpve.setCreationDate(ZonedDateTime.now());
        kpve.setModificationDate(ZonedDateTime.now());
        kpve.setTimeTag(timeTag);
        kpve.setKPValue(1L);
        kpve.setKPValueFlag(KPValueFlag.ZERO);
        kpve.setKPEntity(kpe);
        
        List<KPValueEntity> kpvel = Arrays.asList(kpve);
        kpe.setKPValueList(kpvel);
        return kpe;
    }
}
