package br.inpe.climaespacial.swd.test;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ApplicationScoped
public class EntityManagerProducer {

    @Inject
    @Default
    private EntityManagerFactory defaultEntityManagerFactory;

    @Produces
    @RequestScoped
    public EntityManager createDefaultEntityManager() {
        return createEntityManager(defaultEntityManagerFactory);
    }

    public void dispose(@Disposes EntityManager entityManager) {
        entityManager.getTransaction().rollback();
        entityManager.close();
    }

    private EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.USE);
        em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
        em.getTransaction().begin();

        return em;
    }
}
