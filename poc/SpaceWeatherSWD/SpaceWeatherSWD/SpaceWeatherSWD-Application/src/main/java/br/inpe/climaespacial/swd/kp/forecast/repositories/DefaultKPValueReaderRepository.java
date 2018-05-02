package br.inpe.climaespacial.swd.kp.forecast.repositories;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;

@Dependent
public class DefaultKPValueReaderRepository implements KPValueReaderRepository {

    @Inject
    private EntityManager entityManager;
    
    @Inject
    private KPValueMapper kpValueMapper;
    
    @Override
    public KPValue getLastKPValue() {
        String jpql = "SELECT kpve FROM CIndexEntity cie, KPValueEntity kpve WHERE " 
                + "cie.timeTag = kpve.timeTag AND "
                + "cie.timeTag NOT IN (SELECT kpfe.indexesTimeTag FROM KPForecastEntity kpfe) "
                + "AND cie.timeTag < (SELECT MAX(kpve.timeTag) FROM KPValueEntity kpve) ORDER BY cie.timeTag ASC";
        TypedQuery<KPValueEntity> tq = entityManager.createQuery(jpql, KPValueEntity.class);
        tq.setMaxResults(1);
        List<KPValueEntity> kpvel = tq.getResultList();
        KPValueEntity kpve = kpvel.isEmpty() ? null : kpvel.get(0);
        
        return kpValueMapper.map(kpve);
    }

}
