package br.inpe.climaespacial.swd.indexes.b.dtos;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class BIndexTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private BIndex bIndex;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        bIndex.setTimeTag(timeTag);

        assertEquals(timeTag, bIndex.getTimeTag());
    }

    @Test
    public void preValueTest() {
        bIndex.setPreValue(VALUE);

        assertEquals(VALUE, bIndex.getPreValue(), DELTA);
    }

    @Test
    public void postValueTest() {
        bIndex.setPostValue(VALUE);

        assertEquals(VALUE, bIndex.getPostValue(), DELTA);
    }

}
