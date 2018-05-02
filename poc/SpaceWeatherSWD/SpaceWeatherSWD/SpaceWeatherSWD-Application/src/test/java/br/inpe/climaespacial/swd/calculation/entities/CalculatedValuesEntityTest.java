package br.inpe.climaespacial.swd.calculation.entities;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(CalculatedValuesEntity.class)
public class CalculatedValuesEntityTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private CalculatedValuesEntity calculatedValuesEntity;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        calculatedValuesEntity.setTimeTag(timeTag);

        assertEquals(timeTag, calculatedValuesEntity.getTimeTag());
    }

    @Test
    public void eyTest() {
        calculatedValuesEntity.setEy(VALUE);

        assertEquals(VALUE, calculatedValuesEntity.getEy(), DELTA);
    }

    @Test
    public void dprTest() {
        calculatedValuesEntity.setDpr(VALUE);

        assertEquals(VALUE, calculatedValuesEntity.getDpr(), DELTA);
    }

    @Test
    public void rmpTest() {
        calculatedValuesEntity.setRmp(VALUE);

        assertEquals(VALUE, calculatedValuesEntity.getRmp(), DELTA);
    }

}
