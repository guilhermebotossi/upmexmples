package br.inpe.climaespacial.swd.values.dpr.average.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.average.entities.AverageDPREntity;
import br.inpe.climaespacial.swd.values.dpr.average.mappers.AverageDPRMapper;
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
@AdditionalClasses({DefaultAverageDPRRepository.class})
public class AverageDPRRepositoryTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
    private static final int PERIOD_SIZE = 31;
    private static final String fullyQualifiedName = AverageDPREntity.class.getName();
    private static final String SQL = "SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.dpr) "
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
    private AverageDPRMapper averageDPRMapper;

    @Inject
    private AverageDPRRepository averageDPRRepository;

    @Test
    public void list_calledWithInitialTimeNull_throws() {
        RuntimeException re = null;

        try {
            averageDPRRepository.list(null, null);
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
            averageDPRRepository.list(initialDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"finalDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithValidDates_succedds() {
        TypedQuery<AverageDPREntity> tq = mockTypedQuery(AverageDPREntity.class);
        List<AverageDPREntity> adprel = mockList(AverageDPREntity.class);
        List<AverageDPR> adprl1 = mockList(AverageDPR.class);
        when(entityManager.createQuery(SQL, AverageDPREntity.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(adprel);
        when(averageDPRMapper.map(adprel)).thenReturn(adprl1);

        List<AverageDPR> adprl2 = averageDPRRepository.list(initialDateTime, finalDateTime); 

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(entityManager).createQuery(SQL, AverageDPREntity.class);
        verify(tq).setParameter("initialDateTime", initialDateTime);
        verify(tq).setParameter("finalDateTime", finalDateTime);
        verify(tq).getResultList();
        verify(averageDPRMapper).map(adprel);
        assertNotNull(adprl2);
        assertSame(adprl1, adprl2);
    }

}
