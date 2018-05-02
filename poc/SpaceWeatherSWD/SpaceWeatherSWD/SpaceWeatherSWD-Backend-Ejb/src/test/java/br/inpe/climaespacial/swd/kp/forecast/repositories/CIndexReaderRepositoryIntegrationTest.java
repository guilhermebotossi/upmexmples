package br.inpe.climaespacial.swd.kp.forecast.repositories;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.helpers.DefaultDateTimeHelper;
import br.inpe.climaespacial.swd.commons.utils.DefaultCollectionUtils;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.factories.DefaultCIndexFactory;
import br.inpe.climaespacial.swd.indexes.c.mappers.DefaultCIndexMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultCIndexReaderRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    DefaultCIndexMapper.class,
    DefaultCIndexFactory.class,
    DefaultListFactory.class,
    DefaultDateTimeHelper.class,
    DefaultCollectionUtils.class
})
public class CIndexReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    private static final double VALUE = 1.0;

    @Inject
    private EntityManager entityManager;

    @Inject
    private CIndexReaderRepository cIndexReaderRepository;

    @Test 
    @InRequestScope
    public void getLastCIndexes_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        for(int i = 16; i > 0 ; i--) {
            entityManager.persist(createCIndexEntity(timeTag));
            timeTag = timeTag.minusHours(1);
        }
        
        List<CIndex> cil = cIndexReaderRepository.getLastCIndexes(ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]"));
          
        assertNotNull(cil);
        assertThat(cil, is(not(empty())));
        assertThat(cil, hasSize(9));
        
        ZonedDateTime timeTag1 = ZonedDateTime.parse("2017-01-01T07:00:00z[UTC]");
        for(int i = 0; i < cil.size() ; i++) {
            CIndex zIndex = cil.get(i);
            assertNotNull(zIndex);
            assertEquals(timeTag1, zIndex.getTimeTag());
            timeTag1 = timeTag1.plusHours(1);
        }
    }


    private CIndexEntity createCIndexEntity(ZonedDateTime zonedDateTime) {
        CIndexEntity cie = new CIndexEntity();
        cie.setId(UUID.randomUUID());
        cie.setCreationDate(zonedDateTime);
        cie.setModificationDate(zonedDateTime);
        cie.setTimeTag(zonedDateTime);
        cie.setPreValue(VALUE);
        cie.setPostValue(VALUE);
        return cie;
    }

}
