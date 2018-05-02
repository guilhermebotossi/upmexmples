package br.inpe.climaespacial.swd.values.speed.value.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;
import br.inpe.climaespacial.swd.values.speed.value.entities.SpeedEntity;
import br.inpe.climaespacial.swd.values.speed.value.mappers.SpeedMapper;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultSpeedRepository.class})
public class SpeedRepositoryTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
    private static final int PERIOD_SIZE = 3;
    private static final String fullyQualifiedName = SpeedEntity.class.getName();
    private static final String SQL = "SELECT NEW " + fullyQualifiedName + "(pe.timeTag, pe.speed) "
            + "FROM PlasmaEntity pe "
            + "WHERE "
            + "pe.timeTag >= :initialDateTime and "
            + "pe.timeTag < :finalDateTime ORDER BY pe.timeTag";

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private IntervalValidator intervalValidator;

    @Mock
    @Produces
    private SpeedMapper speedMapper;

    @Inject
    private SpeedRepository speedRepository;

    @Test
    public void list_calledWithInitialTimeNull_throws() {
        RuntimeException re = null;

        try {
            speedRepository.list(null, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"initialDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithFinalTimeNull_throws() {
        RuntimeException re = null;

        try {
            speedRepository.list(initialDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"finalDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithValidDates_succedds() {
        TypedQuery<SpeedEntity> tq = mockTypedQuery(SpeedEntity.class);
        List<SpeedEntity> sel = mockList(SpeedEntity.class);
        List<Speed> sl1 = mockList(Speed.class);
        when(entityManager.createQuery(SQL, SpeedEntity.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(sel);
        when(speedMapper.map(sel)).thenReturn(sl1);

        List<Speed> sl2 = speedRepository.list(initialDateTime, finalDateTime);

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(entityManager).createQuery(SQL, SpeedEntity.class);
        verify(tq).setParameter("initialDateTime", initialDateTime);
        verify(tq).setParameter("finalDateTime", finalDateTime);
        verify(tq).getResultList();
        verify(speedMapper).map(sel);
        assertNotNull(sl2);
        assertSame(sl1, sl2);
    }

}
