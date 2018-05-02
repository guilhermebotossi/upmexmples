package br.inpe.climaespacial.swd.average.repositories;

import br.inpe.climaespacial.swd.average.entities.MagPlasmaCalculatedEntity;
import br.inpe.climaespacial.swd.average.mappers.MagPlasmaCalculatedMapper;
import br.inpe.climaespacial.swd.average.providers.NextHourProvider;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockSimpleEntryWithList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.factories.SimpleEntryFactory;
import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultMagPlasmaCalculatedReaderRepository.class)
public class MagPlasmaCalculatedReaderRepositoryTest {

    private static final String SELECT_SQL = "SELECT NEW br.inpe.climaespacial.swd.average.entities.MagPlasmaCalculatedEntity(me, pe, cve) "
            + "FROM PlasmaEntity pe, MagEntity me, CalculatedValuesEntity cve " + "WHERE me.timeTag = pe.timeTag AND "
            + "me.timeTag = cve.timeTag AND "
            + "pe.timeTag >= :startTimeTag AND pe.timeTag < :endTimeTag";

    @Produces
    @Mock
    private EntityManager entityManager;

    @Produces
    @Mock
    private NextHourProvider nextHour;

    @Produces
    @Mock
    private MagPlasmaCalculatedMapper magPlasmaCalculatedMapper;

    @Produces
    @Mock
    private ListFactory<MagPlasmaCalculated> magPlasmaCalculatedListFactory;

    @Produces
    @Mock
    private SimpleEntryFactory<ZonedDateTime, List<MagPlasmaCalculated>> simpleEntryFactory;

    @Inject
    private MagPlasmaCalculatedReaderRepository mapPlasmaCalculatedReaderRepository;

    @Test
    public void list_called_returnsList() {
        ZonedDateTime szdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime ezdt = ZonedDateTime.parse("2017-01-01T12:59:59z[UTC]");
        when(nextHour.getNextHour()).thenReturn(szdt);
        TypedQuery<MagPlasmaCalculatedEntity> tq = mockTypedQuery(MagPlasmaCalculatedEntity.class);
        when(entityManager.createQuery(SELECT_SQL, MagPlasmaCalculatedEntity.class)).thenReturn(tq);
        List<MagPlasmaCalculatedEntity> mpcel = new ArrayList<>();
        when(tq.getResultList()).thenReturn(mpcel);
        List<MagPlasmaCalculated> mpcl1 = mockList(MagPlasmaCalculated.class);
        SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>> se1 = mockSimpleEntryWithList(ZonedDateTime.class, MagPlasmaCalculated.class);
        when(mpcl1.isEmpty()).thenReturn(false);
        when(magPlasmaCalculatedMapper.map(mpcel)).thenReturn(mpcl1);
        when(simpleEntryFactory.create(szdt, mpcl1)).thenReturn(se1);
        when(se1.getKey()).thenReturn(szdt);
        when(se1.getValue()).thenReturn(mpcl1);

        SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>> se2 = mapPlasmaCalculatedReaderRepository.list();

        assertNotNull(se2);
        assertNotNull(se2.getKey());
        assertSame(szdt, se2.getKey());
        assertNotNull(se2.getValue());
        assertSame(mpcl1, se2.getValue());
        assertThat(se2.getValue(), is(not(empty())));
        verify(nextHour, times(1)).getNextHour();
        verify(entityManager, times(1)).createQuery(SELECT_SQL, MagPlasmaCalculatedEntity.class);
        verify(tq, times(1)).setParameter("startTimeTag", szdt);
        verify(tq, times(1)).setParameter("endTimeTag", ezdt);
        verify(tq, times(1)).getResultList();
        verify(magPlasmaCalculatedMapper, times(1)).map(mpcel);
        verify(simpleEntryFactory, times(1)).create(szdt, mpcl1);
    }

    @Test
    public void list_called_returnsNull() {
        when(nextHour.getNextHour()).thenReturn(null);
        List<MagPlasmaCalculated> mpcl1 = mockList(MagPlasmaCalculated.class);
        when(mpcl1.isEmpty()).thenReturn(true);
        when(magPlasmaCalculatedListFactory.create()).thenReturn(mpcl1);
        SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>> se1 = mockSimpleEntryWithList(ZonedDateTime.class, MagPlasmaCalculated.class);
        when(simpleEntryFactory.create(null, mpcl1)).thenReturn(se1);
        when(se1.getValue()).thenReturn(mpcl1);

        SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>> se2 = mapPlasmaCalculatedReaderRepository.list();

        assertNotNull(se2);
        assertNull(se2.getKey());
        assertNotNull(se2.getValue());
        assertSame(mpcl1, se2.getValue());
        assertThat(se2.getValue(), is(empty()));
        verify(nextHour, times(1)).getNextHour();
        verify(magPlasmaCalculatedListFactory, times(1)).create();

    }
}
