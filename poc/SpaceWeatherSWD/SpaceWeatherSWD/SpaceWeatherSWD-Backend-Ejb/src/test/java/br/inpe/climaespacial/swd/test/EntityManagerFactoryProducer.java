package br.inpe.climaespacial.swd.test;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerFactoryProducer {

    @Produces
    @RequestScoped
    public EntityManagerFactory createDefault() {
        return createEntityManagerFactory("SWD-PU-DERBY");
    }

    private EntityManagerFactory createEntityManagerFactory(String puName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(puName);
        return entityManagerFactory;
    }

    public void dispose(@Disposes EntityManagerFactory entityManagerFactory) {
        entityManagerFactory.close();
    }
}
