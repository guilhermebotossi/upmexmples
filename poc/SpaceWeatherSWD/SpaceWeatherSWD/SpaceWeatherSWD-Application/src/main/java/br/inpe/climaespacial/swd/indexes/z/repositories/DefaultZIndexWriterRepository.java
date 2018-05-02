package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.mappers.ZIndexEntityMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultZIndexWriterRepository implements ZIndexWriterRepository {

    @Inject
    private EntityManager entityManager;
    
    @Inject 
    private ZIndexEntityMapper zIndexEntityMapper;
	
    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(ZIndex zIndex) {

        throwIfNull(zIndex, "zIndex");

    	ZIndexEntity zie = zIndexEntityMapper.map(zIndex);
    	
        entityManager.persist(zie);

        entityManager.flush();
    	
    }
    
}
