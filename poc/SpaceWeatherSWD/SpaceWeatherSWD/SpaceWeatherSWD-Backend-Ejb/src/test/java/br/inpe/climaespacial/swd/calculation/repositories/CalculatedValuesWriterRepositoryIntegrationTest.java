package br.inpe.climaespacial.swd.calculation.repositories;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.calculation.factories.DefaultCalculatedValuesEntityFactory;
import br.inpe.climaespacial.swd.calculation.mappers.DefaultCalculatedValuesEntityMapper;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.CreateTestRecords;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultCalculatedValuesWriterRepository.class,
    DefaultCalculatedValuesEntityMapper.class,
    DefaultCalculatedValuesEntityFactory.class,
    DefaultListFactory.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class CalculatedValuesWriterRepositoryIntegrationTest extends BaseIntegrationTest {

    private CreateTestRecords createRecords = new CreateTestRecords();

    @Inject
    private EntityManager entityManager;

    @Inject
    private CalculatedValuesWriterRepository calculatedValuesWriterRepository;

    @InRequestScope
    @Test
    public void save_called_persistsList() {

        List<CalculatedValues> cvl = createRecords.createCalculatedValuesList();

        calculatedValuesWriterRepository.save(cvl);

        TypedQuery<CalculatedValuesEntity> q = entityManager.createQuery("SELECT cve FROM CalculatedValuesEntity cve", CalculatedValuesEntity.class);
        List<CalculatedValuesEntity> calculatedValuesList = q.getResultList();
        assertEquals(2, calculatedValuesList.size());
    }
}
