package br.inpe.climaespacial.swd.acquisition.repositories;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.mappers.MagEntityMapper;

@Dependent
public class DefaultMagWriterRepository implements MagWriterRepository {

    @Inject
    private MagEntityMapper magEntityMapper;

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(List<Mag> magList) {

        throwIfNull(magList, "magList");

        List<MagEntity> mel = magEntityMapper.map(magList);

        mel.forEach(this::persist);
    }

    private void persist(MagEntity me) {
        TypedQuery<Boolean> tq = entityManager.createQuery(
                "SELECT CASE WHEN(COUNT(*) > 0) THEN true ELSE false END FROM MagEntity me WHERE me.timeTag = :timeTag",
                Boolean.class);
        tq.setParameter("timeTag", me.getTimeTag());

        if (!tq.getSingleResult()) {
            entityManager.persist(me);
        }
    }

}
