package br.inpe.climaespacial.swd.indexes.c.repositories;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.mappers.CIndexEntityMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultCIndexWriterRepository implements CIndexWriterRepository {

    @Inject
    private EntityManager entityManager;
    
    @Inject 
    private CIndexEntityMapper cIndexEntityMapper;
	
    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(CIndex cIndex) {
        throwIfNull(cIndex, "cIndex");

    	CIndexEntity cie = cIndexEntityMapper.map(cIndex);
    	
        entityManager.persist(cie);

        entityManager.flush();
    	
    }
    
}
