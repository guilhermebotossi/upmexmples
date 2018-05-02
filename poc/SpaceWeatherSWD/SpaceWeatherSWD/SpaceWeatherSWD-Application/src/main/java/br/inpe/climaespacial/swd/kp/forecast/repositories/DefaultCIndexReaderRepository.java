package br.inpe.climaespacial.swd.kp.forecast.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.inpe.climaespacial.swd.commons.utils.CollectionUtils;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.mappers.CIndexMapper;

@Dependent
public class DefaultCIndexReaderRepository implements CIndexReaderRepository {

    @Inject
    private EntityManager entityManager;

    @Inject
    private CIndexMapper cIndexMapper;
    
    @Inject
    private CollectionUtils collectionUtils;
    
    @Override
    public List<CIndex> getLastCIndexes(ZonedDateTime timeTag) {
        int maxResults = 9;
        String jpql = "SELECT cie FROM CIndexEntity cie WHERE cie.timeTag <= :timeTag ORDER BY cie.timeTag DESC";
        TypedQuery<CIndexEntity> tq = entityManager.createQuery(jpql, CIndexEntity.class);
        tq.setParameter("timeTag", timeTag);
        tq.setMaxResults(maxResults);
        List<CIndexEntity> ciel = tq.getResultList();
        collectionUtils.reverse(ciel);
        return cIndexMapper.map(ciel);
    }

}
