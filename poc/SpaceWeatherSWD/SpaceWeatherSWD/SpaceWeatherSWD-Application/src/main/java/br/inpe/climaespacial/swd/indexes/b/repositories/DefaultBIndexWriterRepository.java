package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.mappers.BIndexEntityMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultBIndexWriterRepository implements BIndexWriterRepository {

    @Inject
    private EntityManager entityManager;

    @Inject
    private BIndexEntityMapper bIndexEntityMapper;

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(BIndex bIndex) {
        throwIfNull(bIndex, "bIndex");

        BIndexEntity bie = bIndexEntityMapper.map(bIndex);

        entityManager.persist(bie);

        entityManager.flush();
    }

}
