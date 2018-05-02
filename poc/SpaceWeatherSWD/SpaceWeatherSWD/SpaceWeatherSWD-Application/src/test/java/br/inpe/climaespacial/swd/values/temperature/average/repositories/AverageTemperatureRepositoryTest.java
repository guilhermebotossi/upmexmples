package br.inpe.climaespacial.swd.values.temperature.average.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;
import br.inpe.climaespacial.swd.values.temperature.average.entities.AverageTemperatureEntity;
import br.inpe.climaespacial.swd.values.temperature.average.mappers.AverageTemperatureMapper;
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
@AdditionalClasses({DefaultAverageTemperatureRepository.class})
public class AverageTemperatureRepositoryTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
    private static final int PERIOD_SIZE = 31;
    private static final String fullyQualifiedName = AverageTemperatureEntity.class.getName();
    private static final String SQL = "SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.temperature) "
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
    private AverageTemperatureMapper AverageTemperatureMapper;

    @Inject
    private AverageTemperatureRepository averageTemperatureRepository;

    @Test
    public void list_calledWithInitialTimeNull_throws() {
        RuntimeException re = null;

        try {
            averageTemperatureRepository.list(null, null);
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
            averageTemperatureRepository.list(initialDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"finalDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithValidDates_succedds() {
        TypedQuery<AverageTemperatureEntity> tq = mockTypedQuery(AverageTemperatureEntity.class);
        List<AverageTemperatureEntity> atel = mockList(AverageTemperatureEntity.class);
        List<AverageTemperature> atl1 = mockList(AverageTemperature.class);
        when(entityManager.createQuery(SQL, AverageTemperatureEntity.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(atel);
        when(AverageTemperatureMapper.map(atel)).thenReturn(atl1);

        List<AverageTemperature> atl2 = averageTemperatureRepository.list(initialDateTime, finalDateTime);

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(entityManager).createQuery(SQL, AverageTemperatureEntity.class);
        verify(tq).setParameter("initialDateTime", initialDateTime);
        verify(tq).setParameter("finalDateTime", finalDateTime);
        verify(tq).getResultList();
        verify(AverageTemperatureMapper).map(atel);
        assertNotNull(atl2);
        assertSame(atl1, atl2);
    }

}
