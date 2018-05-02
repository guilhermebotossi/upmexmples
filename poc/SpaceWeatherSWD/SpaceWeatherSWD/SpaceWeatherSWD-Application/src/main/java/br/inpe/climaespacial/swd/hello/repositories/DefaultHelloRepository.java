package br.inpe.climaespacial.swd.hello.repositories;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.exceptions.DataException;
import br.inpe.climaespacial.swd.hello.dtos.Hello;
import br.inpe.climaespacial.swd.hello.entities.HelloEntity;
import br.inpe.climaespacial.swd.hello.factories.HelloEntityFactory;
import br.inpe.climaespacial.swd.hello.mappers.HelloMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.createCustom;

@Dependent
public class DefaultHelloRepository implements HelloRepository {

    @Inject
    private DateTimeProvider dateTimeProvider;

    @Inject
    private HelloEntityFactory helloEntityFactory;

    @Inject
    private HelloMapper helloMapper;

    @Inject
    private EntityManager entityManager;

    @Override
    public void save() {
        try {
            HelloEntity he = findFirst();

            if (he == null) {
                he = helloEntityFactory.create();
            }

            he.setModificationDate(dateTimeProvider.getNow());

            entityManager.merge(he);
            entityManager.flush();
        } catch (Exception e) {
            throw createCustom("Não foi possível salvar hello.", e, DataException.class);
        }
    }

    @Override
    public Hello getHello() {
        try {
            return helloMapper.map(findFirst());
        } catch (Exception e) {
            throw createCustom(
                    "Não foi possível obter a última data, verifique se o método save foi chamado ao menos uma vez.", e,
                    DataException.class);
        }
    }

    private HelloEntity findFirst() {
        String sql = "SELECT h FROM HelloEntity h";
        Query query = entityManager.createQuery(sql);
        @SuppressWarnings("unchecked")
        List<HelloEntity> hel = query.setMaxResults(1).getResultList();
        return !hel.isEmpty() ? hel.get(0) : null;
    }
}
