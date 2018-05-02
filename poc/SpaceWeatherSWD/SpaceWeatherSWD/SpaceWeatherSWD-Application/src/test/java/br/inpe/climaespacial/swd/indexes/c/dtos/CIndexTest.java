package br.inpe.climaespacial.swd.indexes.c.dtos;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class CIndexTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private CIndex cIndex;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        cIndex.setTimeTag(timeTag);

        assertEquals(timeTag, cIndex.getTimeTag());
    }

    @Test
    public void preValueTest() {
        cIndex.setPreValue(VALUE);

        assertEquals(VALUE, cIndex.getPreValue(), DELTA);
    }

    @Test
    public void postValueTest() {
        cIndex.setPostValue(VALUE);

        assertEquals(VALUE, cIndex.getPostValue(), DELTA);
    }

}
