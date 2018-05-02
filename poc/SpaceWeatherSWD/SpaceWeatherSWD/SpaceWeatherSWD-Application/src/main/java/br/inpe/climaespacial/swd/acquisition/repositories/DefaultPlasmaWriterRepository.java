package br.inpe.climaespacial.swd.acquisition.repositories;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.acquisition.mappers.PlasmaEntityMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultPlasmaWriterRepository implements PlasmaWriterRepository {

    @Inject
    private PlasmaEntityMapper plasmaEntityMapper;

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(List<Plasma> plasmaList) {

        throwIfNull(plasmaList, "plasmaList");

        List<PlasmaEntity> pel = plasmaEntityMapper.map(plasmaList);

        pel.forEach(this::persist);
    }

    private void persist(PlasmaEntity pe) {
        TypedQuery<Boolean> tq = entityManager.createQuery(
                "SELECT CASE WHEN(COUNT(*) > 0) THEN true ELSE false END FROM PlasmaEntity pe WHERE pe.timeTag = :timeTag",
                Boolean.class);
        tq.setParameter("timeTag", pe.getTimeTag());

        if (!tq.getSingleResult()) {
            entityManager.persist(pe);
        }
    }
    
}
