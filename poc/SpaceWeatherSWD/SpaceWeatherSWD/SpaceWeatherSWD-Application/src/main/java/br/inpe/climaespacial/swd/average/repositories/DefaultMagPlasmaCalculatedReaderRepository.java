package br.inpe.climaespacial.swd.average.repositories;

import br.inpe.climaespacial.swd.average.entities.MagPlasmaCalculatedEntity;
import br.inpe.climaespacial.swd.average.mappers.MagPlasmaCalculatedMapper;
import br.inpe.climaespacial.swd.average.providers.NextHourProvider;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.factories.SimpleEntryFactory;
import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultMagPlasmaCalculatedReaderRepository implements MagPlasmaCalculatedReaderRepository {

    private static final String SQL_SELECT = "SELECT NEW br.inpe.climaespacial.swd.average.entities.MagPlasmaCalculatedEntity(me, pe, cve) "
            + "FROM PlasmaEntity pe, MagEntity me, CalculatedValuesEntity cve "
            + "WHERE me.timeTag = pe.timeTag AND "
            + "me.timeTag = cve.timeTag AND "
            + "pe.timeTag >= :startTimeTag AND pe.timeTag < :endTimeTag";

    @Inject
    private EntityManager entityManager;

    @Inject
    private NextHourProvider nextHourProvider;

    @Inject
    private MagPlasmaCalculatedMapper magPlasmaCalculatedMapper;

    @Inject
    private ListFactory<MagPlasmaCalculated> magPlasmaCalculatedListFactory;

    @Inject
    private SimpleEntryFactory<ZonedDateTime, List<MagPlasmaCalculated>> simpleEntryFactory;

    @Override
    public SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>> list() {
        ZonedDateTime szdt = nextHourProvider.getNextHour();
        if (szdt != null) {
            ZonedDateTime ezdt = szdt.plusMinutes(59).plusSeconds(59);

            List<MagPlasmaCalculatedEntity> mpcel = getData(szdt, ezdt);

            return simpleEntryFactory.create(szdt, magPlasmaCalculatedMapper.map(mpcel));
        }

        return simpleEntryFactory.create(szdt, magPlasmaCalculatedListFactory.create());
    }

    private List<MagPlasmaCalculatedEntity> getData(ZonedDateTime startZonedDateTime, ZonedDateTime endZonedDateTime) {
        TypedQuery<MagPlasmaCalculatedEntity> tq = entityManager.createQuery(SQL_SELECT, MagPlasmaCalculatedEntity.class);
        tq.setParameter("startTimeTag", startZonedDateTime);
        tq.setParameter("endTimeTag", endZonedDateTime);
        return tq.getResultList();
    }

}
