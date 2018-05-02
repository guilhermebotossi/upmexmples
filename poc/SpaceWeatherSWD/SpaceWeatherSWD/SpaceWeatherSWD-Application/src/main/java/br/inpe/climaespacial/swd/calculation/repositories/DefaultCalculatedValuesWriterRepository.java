package br.inpe.climaespacial.swd.calculation.repositories;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.calculation.mappers.CalculatedValuesEntityMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultCalculatedValuesWriterRepository implements CalculatedValuesWriterRepository {

    @Inject
    private CalculatedValuesEntityMapper calculatedValuesEntityMapper;

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(List<CalculatedValues> calculatedValuesList) {

        throwIfNull(calculatedValuesList, "calculatedValuesList");

        List<CalculatedValuesEntity> cvel = calculatedValuesEntityMapper.map(calculatedValuesList);

        cvel.forEach((cve) -> {
            entityManager.persist(cve);
        });

        entityManager.flush();
        entityManager.clear();
    }
}
