package br.inpe.climaespacial.swd.calculation.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.calculation.factories.DefaultMagPlasmaFactory;
import br.inpe.climaespacial.swd.calculation.mappers.DefaultMagPlasmaMapper;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.CreateTestRecords;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({ HelperFactoryProducer.class, DefaultMagPlasmaReaderRepository.class, DefaultMagPlasmaMapper.class,
        DefaultMagPlasmaFactory.class, DefaultListFactory.class, EntityManagerFactoryProducer.class,
        EntityManagerProducer.class })
public class MagPlasmaReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    private CreateTestRecords createRecords = new CreateTestRecords();

    @Inject
    private EntityManager entityManager;

    @Inject
    private MagPlasmaReaderRepository magPlasmaReaderRepository;

    @InRequestScope
    @Test
    public void list_called_returnsList() {
        List<MagEntity> mel = createRecords.createMagEntityList();
        List<PlasmaEntity> pel = createRecords.createPlasmaEntityList();

        mel.forEach(me -> {
            entityManager.persist(me);
        });

        pel.forEach(pe -> {
            entityManager.persist(pe);
        });

        List<MagPlasma> mpl2 = magPlasmaReaderRepository.list();

        assertNotNull(mpl2);
        assertEquals(2, mpl2.size());
    }

    @InRequestScope
    @Test
    public void list_called_returnsListEmpty() {
        List<MagEntity> mel = createRecords.createMagEntityList();
        List<PlasmaEntity> pel = createRecords.createPlasmaEntityList();
        List<CalculatedValuesEntity> cvel = createRecords.createCalculatedValuesEntityList();

        mel.forEach(me -> {
            entityManager.persist(me);
        });

        pel.forEach(pe -> {
            entityManager.persist(pe);
        });

        cvel.forEach(cve -> {
            entityManager.persist(cve);
        });

        List<MagPlasma> mpl2 = magPlasmaReaderRepository.list();

        assertNotNull(mpl2);
        assertEquals(0, mpl2.size());
    }

}
