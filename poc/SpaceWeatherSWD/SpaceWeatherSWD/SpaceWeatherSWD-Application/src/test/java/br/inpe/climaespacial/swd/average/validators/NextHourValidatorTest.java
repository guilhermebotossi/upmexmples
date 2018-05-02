package br.inpe.climaespacial.swd.average.validators;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import java.time.ZonedDateTime;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultNextHourValidator.class)
public class NextHourValidatorTest {

    private static final String SQL_SELECT = "SELECT "
            + "CASE WHEN(COUNT(*) > 0) THEN true ELSE false END "
            + "FROM MagEntity me, PlasmaEntity pe, CalculatedValuesEntity cve "
            + "WHERE me.timeTag = pe.timeTag "
            + "AND me.timeTag = cve.timeTag "
            + "AND me.timeTag >= :initialDateTime";

    @Mock
    @Produces
    private EntityManager entityManager;

    @Inject
    private NextHourValidator nextHourValidator;

    @Test
    public void validate_called_returnsTrue() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:39:50z[UTC]");
        ZonedDateTime nextHour = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        TypedQuery<Boolean> tq = mockTypedQuery(Boolean.class);
        when(entityManager.createQuery(SQL_SELECT, Boolean.class)).thenReturn(tq);
        when(tq.setParameter("initialDateTime", nextHour)).thenReturn(tq);
        when(tq.getSingleResult()).thenReturn(true);

        Boolean validated = nextHourValidator.validate(zdt);

        assertTrue(validated);
        verify(entityManager).createQuery(SQL_SELECT, Boolean.class);
        verify(tq).setParameter("initialDateTime", nextHour);
        verify(tq).getSingleResult();
    }

    @Test
    public void validate_called_returnsFalse() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:39:50z[UTC]");
        ZonedDateTime nextHour = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        TypedQuery<Boolean> tq = mockTypedQuery(Boolean.class);
        when(entityManager.createQuery(SQL_SELECT, Boolean.class)).thenReturn(tq);
        when(tq.setParameter("initialDateTime", nextHour)).thenReturn(tq);
        when(tq.getSingleResult()).thenReturn(false);

        Boolean validated = nextHourValidator.validate(zdt);

        assertFalse(validated);
        verify(entityManager).createQuery(SQL_SELECT, Boolean.class);
        verify(tq).setParameter("initialDateTime", nextHour);
        verify(tq).getSingleResult();
    }
}
