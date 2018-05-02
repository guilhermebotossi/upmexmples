package br.inpe.climaespacial.swd.commons.factories;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EntityManagerProducerTest {

    @Test
    public void defaultConstructor_called_returnsInstance() {
        EntityManagerProducer emp = new EntityManagerProducer();

        assertNotNull(emp);
    }

    @Test
    public void createDefaultEntityManager_called_setPropertiesCorrectly() {
        EntityManagerFactory defaultEntityManagerFactory = mock(EntityManagerFactory.class);
        EntityManager em = mock(EntityManager.class);
        when(defaultEntityManagerFactory.createEntityManager()).thenReturn(em);

        EntityManagerProducer emp = new EntityManagerProducer(defaultEntityManagerFactory);
        EntityManager eem = emp.createDefaultEntityManager();

        assertNotNull(eem);
        assertSame(eem, em);
        verify(defaultEntityManagerFactory).createEntityManager();
        verify(em).setProperty("javax.persistence.cache.storeMode", CacheStoreMode.USE);
        verify(em).setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
    }

    @Test
    public void dispose_called_disposes() {
        EntityManagerFactory defaultEntityManagerFactory = mock(EntityManagerFactory.class);
        EntityManager em = mock(EntityManager.class);

        EntityManagerProducer emp = new EntityManagerProducer(defaultEntityManagerFactory);
        emp.dispose(em);

        verify(em).close();
    }

}
