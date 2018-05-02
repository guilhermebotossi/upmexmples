package br.inpe.climaespacial.swd.values.density.value.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.density.value.entities.DensityEntity;
import br.inpe.climaespacial.swd.values.density.value.mappers.DensityMapper;
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
@AdditionalClasses({DefaultDensityRepository.class})
public class DensityRepositoryTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
    private static final int PERIOD_SIZE = 3;
    private static final String fullyQualifiedName = DensityEntity.class.getName();
    private static final String SQL = "SELECT NEW " + fullyQualifiedName + "(pe.timeTag, pe.density) "
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
    private DensityMapper densityMapper;

    @Inject
    private DensityRepository densityRepository;

    @Test
    public void list_calledWithInitialTimeNull_throws() {
        RuntimeException re = null;

        try {
            densityRepository.list(null, null);
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
            densityRepository.list(initialDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"finalDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithValidDates_succedds() {
        TypedQuery<DensityEntity> tq = mockTypedQuery(DensityEntity.class);
        List<DensityEntity> del = mockList(DensityEntity.class);
        List<Density> dl = mockList(Density.class);
        when(entityManager.createQuery(SQL, DensityEntity.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(del);
        when(densityMapper.map(del)).thenReturn(dl);

        List<Density> dl2 = densityRepository.list(initialDateTime, finalDateTime);

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(entityManager).createQuery(SQL, DensityEntity.class);
        verify(tq).setParameter("initialDateTime", initialDateTime);
        verify(tq).setParameter("finalDateTime", finalDateTime);
        verify(tq).getResultList();
        verify(densityMapper).map(del);
        assertNotNull(dl2);
        assertSame(dl, dl2);
    }

}
