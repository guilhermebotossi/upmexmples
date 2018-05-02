package br.inpe.climaespacial.swd.values.ey.average.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;
import br.inpe.climaespacial.swd.values.ey.average.entities.AverageEYEntity;
import br.inpe.climaespacial.swd.values.ey.average.mappers.AverageEYMapper;
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
@AdditionalClasses({DefaultAverageEYRepository.class})
public class AverageEYRepositoryTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
    private static final int PERIOD_SIZE = 31;
    private static final String fullyQualifiedName = AverageEYEntity.class.getName();
    private static final String SQL = "SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.ey) "
            + "FROM HourlyAverageEntity hae "
            + "WHERE "
            + "hae.timeTag >= :initialDateTime and "
            + "hae.timeTag < :finalDateTime ORDER BY hae.timeTag";

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private IntervalValidator intervalValidator;

    @Mock
    @Produces
    private AverageEYMapper averageEYMapper;

    @Inject
    private AverageEYRepository averageEYRepository;

    @Test
    public void list_calledWithInitialTimeNull_throws() {
        RuntimeException re = null;

        try {
            averageEYRepository.list(null, null);
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
            averageEYRepository.list(initialDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"finalDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithValidDates_succedds() {
        TypedQuery<AverageEYEntity> tq = mockTypedQuery(AverageEYEntity.class);
        List<AverageEYEntity> aeyel = mockList(AverageEYEntity.class);
        List<AverageEY> aeyl1 = mockList(AverageEY.class);
        when(entityManager.createQuery(SQL, AverageEYEntity.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(aeyel);
        when(averageEYMapper.map(aeyel)).thenReturn(aeyl1);

        List<AverageEY> aeyl2 = averageEYRepository.list(initialDateTime, finalDateTime);

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(entityManager).createQuery(SQL, AverageEYEntity.class);
        verify(tq).setParameter("initialDateTime", initialDateTime);
        verify(tq).setParameter("finalDateTime", finalDateTime);
        verify(tq).getResultList();
        verify(averageEYMapper).map(aeyel);
        assertNotNull(aeyl2);
        assertSame(aeyl1, aeyl2);
    }

}
