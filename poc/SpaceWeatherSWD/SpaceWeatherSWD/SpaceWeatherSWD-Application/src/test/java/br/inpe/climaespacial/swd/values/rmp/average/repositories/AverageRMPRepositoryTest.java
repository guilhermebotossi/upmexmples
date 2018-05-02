package br.inpe.climaespacial.swd.values.rmp.average.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;
import br.inpe.climaespacial.swd.values.rmp.average.entities.AverageRMPEntity;
import br.inpe.climaespacial.swd.values.rmp.average.mappers.AverageRMPMapper;
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
@AdditionalClasses({DefaultAverageRMPRepository.class})
public class AverageRMPRepositoryTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
    private static final int PERIOD_SIZE = 31;
    private static final String fullyQualifiedName = AverageRMPEntity.class.getName();
    private static final String SQL = "SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.rmp) "
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
    private AverageRMPMapper averageRMPMapper;

    @Inject
    private AverageRMPRepository averageRMPRepository;

    @Test
    public void list_calledWithInitialTimeNull_throws() {
        RuntimeException re = null;

        try {
            averageRMPRepository.list(null, null);
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
            averageRMPRepository.list(initialDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"finalDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithValidDates_succedds() {
        TypedQuery<AverageRMPEntity> tq = mockTypedQuery(AverageRMPEntity.class);
        List<AverageRMPEntity> armpel = mockList(AverageRMPEntity.class);
        List<AverageRMP> armpl1 = mockList(AverageRMP.class);
        when(entityManager.createQuery(SQL, AverageRMPEntity.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(armpel);
        when(averageRMPMapper.map(armpel)).thenReturn(armpl1);

        List<AverageRMP> armpl2 = averageRMPRepository.list(initialDateTime, finalDateTime);

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(entityManager).createQuery(SQL, AverageRMPEntity.class);
        verify(tq).setParameter("initialDateTime", initialDateTime);
        verify(tq).setParameter("finalDateTime", finalDateTime);
        verify(tq).getResultList();
        verify(averageRMPMapper).map(armpel);
        assertNotNull(armpl2);
        assertSame(armpl1, armpl2);
    }

}
