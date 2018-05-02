package br.inpe.climaespacial.swd.kp.values.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;
import br.inpe.climaespacial.swd.kp.values.mappers.KPForecastMapper;
import javax.transaction.Transactional;

@Dependent
public class DefaultKPForecastReaderRepository implements KPForecastReaderRepository {

    @Inject
    private KPForecastMapper kpForecastMapper;
    
    @Inject
    private EntityManager entityManager;
    
    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public List<KPForecast> getNextForecasts(ZonedDateTime zonedDateTime) {
        String sql = "SELECT kpfe FROM KPForecastEntity kpfe where kpfe.indexesTimeTag=:indexesTimeTag";
        TypedQuery<KPForecastEntity> tq = entityManager.createQuery(sql, KPForecastEntity.class);
        tq.setParameter("indexesTimeTag", zonedDateTime);
        List<KPForecastEntity> kpfel = tq.getResultList();
        
        return kpForecastMapper.map(kpfel);
    }

}
