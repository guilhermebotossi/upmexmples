package br.inpe.climaespacial.swd.values.temperature.value.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;
import br.inpe.climaespacial.swd.values.temperature.value.entities.TemperatureEntity;
import br.inpe.climaespacial.swd.values.temperature.value.mappers.TemperatureMapper;
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
@AdditionalClasses({DefaultTemperatureRepository.class})
public class TemperatureRepositoryTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
    private static final int PERIOD_SIZE = 3;
    private static final String fullyQualifiedName = TemperatureEntity.class.getName();
    private static final String SQL = "SELECT NEW " + fullyQualifiedName + "(pe.timeTag, pe.temperature) "
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
    private TemperatureMapper temperatureMapper;

    @Inject
    private TemperatureRepository tempertureRepository;

    @Test
    public void list_calledWithInitialTimeNull_throws() {
        RuntimeException re = null;

        try {
            tempertureRepository.list(null, null);
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
            tempertureRepository.list(initialDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"finalDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithValidDates_succedds() {
        TypedQuery<TemperatureEntity> tq = mockTypedQuery(TemperatureEntity.class);
        List<TemperatureEntity> tel = mockList(TemperatureEntity.class);
        List<Temperature> tl1 = mockList(Temperature.class);
        when(entityManager.createQuery(SQL, TemperatureEntity.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(tel);
        when(temperatureMapper.map(tel)).thenReturn(tl1);

        List<Temperature> tl2 = tempertureRepository.list(initialDateTime, finalDateTime);

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(entityManager).createQuery(SQL, TemperatureEntity.class);
        verify(tq).setParameter("initialDateTime", initialDateTime);
        verify(tq).setParameter("finalDateTime", finalDateTime);
        verify(tq).getResultList();
        verify(temperatureMapper).map(tel);
        assertNotNull(tl2);
        assertSame(tl1, tl2);
    }

}
