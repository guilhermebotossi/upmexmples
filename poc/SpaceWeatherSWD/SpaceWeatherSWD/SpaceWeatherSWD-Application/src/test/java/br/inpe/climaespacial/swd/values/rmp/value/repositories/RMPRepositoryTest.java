package br.inpe.climaespacial.swd.values.rmp.value.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.rmp.value.entities.RMPEntity;
import br.inpe.climaespacial.swd.values.rmp.value.mappers.RMPMapper;
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
@AdditionalClasses({DefaultRMPRepository.class})
public class RMPRepositoryTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
    private static final int PERIOD_SIZE = 3;
    private static final String fullyQualifiedName = RMPEntity.class.getName();
    private static final String SQL = "SELECT NEW " + fullyQualifiedName + "(cve.timeTag, cve.rmp) "
            + "FROM CalculatedValuesEntity cve "
            + "WHERE "
            + "cve.timeTag >= :initialDateTime and "
            + "cve.timeTag < :finalDateTime ORDER BY cve.timeTag";

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private IntervalValidator intervalValidator;

    @Mock
    @Produces
    private RMPMapper rmpMapper;

    @Inject
    private RMPRepository rmpRepository;

    @Test
    public void list_calledWithInitialTimeNull_throws() {
        RuntimeException re = null;

        try {
            rmpRepository.list(null, null);
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
            rmpRepository.list(initialDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"finalDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void list_calledWithValidDates_succedds() {
        TypedQuery<RMPEntity> tq = mockTypedQuery(RMPEntity.class);
        List<RMPEntity> rmpel = mockList(RMPEntity.class);
        List<RMP> rmpl1 = mockList(RMP.class);
        when(entityManager.createQuery(SQL, RMPEntity.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(rmpel);
        when(rmpMapper.map(rmpel)).thenReturn(rmpl1);

        List<RMP> rmpl2 = rmpRepository.list(initialDateTime, finalDateTime);

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(entityManager).createQuery(SQL, RMPEntity.class);
        verify(tq).setParameter("initialDateTime", initialDateTime);
        verify(tq).setParameter("finalDateTime", finalDateTime);
        verify(tq).getResultList();
        verify(rmpMapper).map(rmpel);
        assertNotNull(rmpl2);
        assertSame(rmpl1, rmpl2);
    }

}
