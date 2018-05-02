package br.inpe.climaespacial.swd.kp.forecast.repositories;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;
import br.inpe.climaespacial.swd.kp.forecast.mappers.KPForecastEntityMapper;

@Dependent
public class DefaultKpForecastWriterRepository implements KPForecastWriterRepository {

    @Inject
    private EntityManager entityManager;
    
    @Inject
    private KPForecastEntityMapper kpForecastEntityMapper;
    
    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(List<KPForecast> kpForecastList) {
        throwIfNull(kpForecastList, "kpForecastList");
        
        List<KPForecastEntity> kpfl = kpForecastEntityMapper.map(kpForecastList);
        
        kpfl.forEach(kpfe -> {
            entityManager.persist(kpfe);
        });
        
        entityManager.flush();
        entityManager.clear();
    }

}
