package br.inpe.climaespacial.swd.calculation.repositories;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import br.inpe.climaespacial.swd.calculation.entities.MagPlasmaEntity;
import br.inpe.climaespacial.swd.calculation.mappers.MagPlasmaMapper;

@Dependent
public class DefaultMagPlasmaReaderRepository implements MagPlasmaReaderRepository {

    @Inject
    private MagPlasmaMapper magPlasmaMapper;

    @Inject
    private EntityManager entityManager;

    @Override
    public List<MagPlasma> list() {
        String jpql = "SELECT NEW br.inpe.climaespacial.swd.calculation.entities.MagPlasmaEntity(me, pe) "
                + "FROM PlasmaEntity pe, MagEntity me " + "WHERE me.timeTag = pe.timeTag AND "
                + "NOT EXISTS (SELECT cv.timeTag FROM CalculatedValuesEntity cv WHERE cv.timeTag = pe.timeTag) "
                + "ORDER BY pe.timeTag ASC";
        TypedQuery<MagPlasmaEntity> tq = entityManager.createQuery(jpql, MagPlasmaEntity.class);
        int minutesInDay = 1440;
        tq.setMaxResults(minutesInDay);
        List<MagPlasmaEntity> mpel = tq.getResultList();
        List<MagPlasma> mpl = magPlasmaMapper.map(mpel);

        return mpl;
    }
    
}
