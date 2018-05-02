package br.inpe.climaespacial.swd.values.bt.average.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;
import br.inpe.climaespacial.swd.values.bt.average.entities.AverageBTEntity;
import br.inpe.climaespacial.swd.values.bt.average.mappers.AverageBTMapper;
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
@AdditionalClasses({DefaultAverageBTRepository.class})
public class AverageBTRepositoryTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
    private static final int PERIOD_SIZE = 31;
    private static final String fullyQualifiedName = AverageBTEntity.class.getName();
    private static final String SQL = "SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.bt) "
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
    private AverageBTMapper averageBTMapper;

    @Inject
    private AverageBTRepository averageBTRepository; 

    @Test
    public void list_calledWithInitialTimeNull_throws() {
        RuntimeException re = null;

        try {
            averageBTRepository.list(null, null);
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
            averageBTRepository.list(initialDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"finalDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithValidDates_succedds() {
        TypedQuery<AverageBTEntity> tq = mockTypedQuery(AverageBTEntity.class);
        List<AverageBTEntity> abel = mockList(AverageBTEntity.class);
        List<AverageBT> abl1 = mockList(AverageBT.class);
        when(entityManager.createQuery(SQL, AverageBTEntity.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(abel);
        when(averageBTMapper.map(abel)).thenReturn(abl1);

        List<AverageBT> bl2 = averageBTRepository.list(initialDateTime, finalDateTime);

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(entityManager).createQuery(SQL, AverageBTEntity.class);
        verify(tq).setParameter("initialDateTime", initialDateTime);
        verify(tq).setParameter("finalDateTime", finalDateTime);
        verify(tq).getResultList();
        verify(averageBTMapper).map(abel);
        assertNotNull(bl2);
        assertSame(abl1, bl2);
    }

}
