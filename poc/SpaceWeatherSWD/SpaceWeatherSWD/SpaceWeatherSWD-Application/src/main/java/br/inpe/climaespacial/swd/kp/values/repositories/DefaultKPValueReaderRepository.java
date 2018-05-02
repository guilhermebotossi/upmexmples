package br.inpe.climaespacial.swd.kp.values.repositories;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.inpe.climaespacial.swd.commons.utils.CollectionUtils;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.forecast.repositories.KPValueMapper;

@Dependent
public class DefaultKPValueReaderRepository implements KPValueReaderRepository {

    @Inject
    private KPValueMapper kpValueMapper;

    @Inject
    private EntityManager entityManager;
    
    @Inject
    private CollectionUtils collectionUtils;

    @Override
    @Transactional
    public List<KPValue> getLastKPIndexes() {
        String jpql = "SELECT kpve FROM KPValueEntity kpve WHERE kpve. timeTag < (SELECT MAX(kpve.timeTag) FROM KPValueEntity kpve) ORDER BY kpve.timeTag DESC";

        TypedQuery<KPValueEntity> tq = entityManager.createQuery(jpql, KPValueEntity.class);
        tq.setMaxResults(4);

        List<KPValueEntity> kpvel = tq.getResultList();
        List<KPValue> kpvList = kpValueMapper.map(kpvel);
        collectionUtils.reverse(kpvList);

        return kpvList;
    }
}
