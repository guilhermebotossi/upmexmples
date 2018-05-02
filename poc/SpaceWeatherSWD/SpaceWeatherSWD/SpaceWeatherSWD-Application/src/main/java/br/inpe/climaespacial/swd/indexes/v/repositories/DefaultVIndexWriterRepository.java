package br.inpe.climaespacial.swd.indexes.v.repositories;

import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.mappers.VIndexEntityMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultVIndexWriterRepository implements VIndexWriterRepository {

    @Inject
    private EntityManager entityManager;

    @Inject
    private VIndexEntityMapper vIndexEntityMapper;

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(VIndex vIndex) {

        throwIfNull(vIndex, "vIndex");

        VIndexEntity vie = vIndexEntityMapper.map(vIndex);

        entityManager.persist(vie);

        entityManager.flush();
    }

}
