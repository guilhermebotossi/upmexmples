package br.inpe.climaespacial.swd.home.validators;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultIntervalValidator.class)
public class IntervalValidatorTest {

    private static final int PERIOD_SIZE = 31;

    @Inject
    private IntervalValidator intervalValidator;

    @Test
    public void validate_calledWithFarthestFromNowNull_throws() {
        ArgumentException ae = null;

        ZonedDateTime farthestFromNow = null;
        ZonedDateTime nearestFromNow = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        try {
            intervalValidator.validate(farthestFromNow, nearestFromNow, PERIOD_SIZE);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals("Argument \"farthestFromNow\" cannot be null.", ae.getMessage());
    }

    @Test
    public void validate_calledWithNearestFromNowNull_throws() {
        ArgumentException ae = null;

        ZonedDateTime farthestFromNow = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nearestFromNow = null;

        try {
            intervalValidator.validate(farthestFromNow, nearestFromNow, PERIOD_SIZE);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals("Argument \"nearestFromNow\" cannot be null.", ae.getMessage());
    }

    @Test
    public void validate_calledWithFarthestFromNowAfterThanNearestFromNowNull_throws() {
        ArgumentException ae = null;

        ZonedDateTime farthestFromNow = ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]");
        ZonedDateTime nearestFromNow = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        try {
            intervalValidator.validate(farthestFromNow, nearestFromNow, PERIOD_SIZE);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals("Argument \"farthestFromNow\" cannot be after than \"nearestFromNow\".", ae.getMessage());
    }

    @Test
    public void validate_calledWithPeriodSizeOverLimit_throws() {
        ArgumentException ae = null;

        ZonedDateTime farthestFromNow = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nearestFromNow = ZonedDateTime.parse("2017-03-01T12:00:00z[UTC]");

        try {
            intervalValidator.validate(farthestFromNow, nearestFromNow, PERIOD_SIZE);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals("Period size exceeded.", ae.getMessage());
    }

    @Test
    public void validate_calledWithPeriodSizeEqualToLimit_succeeds() {
        RuntimeException re = null;

        ZonedDateTime farthestFromNow = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nearestFromNow = ZonedDateTime.parse("2017-02-01T12:00:00z[UTC]");

        try {
            intervalValidator.validate(farthestFromNow, nearestFromNow, PERIOD_SIZE);
        } catch (RuntimeException r) {
            re = r;
        }

        assertNull(re);
    }

}
