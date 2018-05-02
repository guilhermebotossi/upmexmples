package br.inpe.climaespacial.swd.commons.factories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit(unitName = "SWD-PU")
    private EntityManagerFactory defaultEntityManagerFactory;

    @Inject
    public EntityManagerProducer() {
    }

    public EntityManagerProducer(EntityManagerFactory defaultEntityManagerFactory) {
        this.defaultEntityManagerFactory = defaultEntityManagerFactory;
    }

    @Produces
    @RequestScoped
    public EntityManager createDefaultEntityManager() {
        return setProperties(defaultEntityManagerFactory);
    }

    public void dispose(@Disposes EntityManager entityManager) {
        entityManager.close();
    }

    private EntityManager setProperties(EntityManagerFactory entityManagerFactory) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.USE);
        em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);

        return em;
    }

}
