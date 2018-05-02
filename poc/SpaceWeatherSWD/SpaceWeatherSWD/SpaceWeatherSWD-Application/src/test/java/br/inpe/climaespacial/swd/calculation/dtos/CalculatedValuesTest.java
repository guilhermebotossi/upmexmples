package br.inpe.climaespacial.swd.calculation.dtos;

import java.time.ZonedDateTime;
import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;

@RunWith(CdiRunner.class)
@AdditionalClasses(CalculatedValues.class)
public class CalculatedValuesTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private CalculatedValues calculatedValues;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        calculatedValues.setTimeTag(timeTag);

        assertEquals(timeTag, calculatedValues.getTimeTag());
    }

    @Test
    public void eyTest() {
        calculatedValues.setEy(VALUE);

        assertEquals(VALUE, calculatedValues.getEy(), DELTA);
    }

    @Test
    public void dprTest() {
        calculatedValues.setDpr(VALUE);

        assertEquals(VALUE, calculatedValues.getDpr(), DELTA);
    }

    @Test
    public void rmpTest() {
        calculatedValues.setRmp(VALUE);

        assertEquals(VALUE, calculatedValues.getRmp(), DELTA);
    }

    @Test
    public void toString_called_returnsStringRepresentation() {
        calculatedValues.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        calculatedValues.setEy(VALUE);
        calculatedValues.setDpr(VALUE + 1);
        calculatedValues.setRmp(VALUE + 2);

        String r = calculatedValues.toString();

        assertEquals("CalculatedValues [timeTag=2017-01-01T12:00Z[UTC], ey=1.0, dpr=2.0, rmp=3.0]", r);
    }

}
