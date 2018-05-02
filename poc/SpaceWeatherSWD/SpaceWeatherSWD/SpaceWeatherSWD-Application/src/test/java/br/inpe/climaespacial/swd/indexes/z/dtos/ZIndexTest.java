package br.inpe.climaespacial.swd.indexes.z.dtos;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class ZIndexTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private ZIndex zIndex;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        zIndex.setTimeTag(timeTag);

        assertEquals(timeTag, zIndex.getTimeTag());
    }

    @Test
    public void preValueTest() {
        zIndex.setPreValue(VALUE);

        assertEquals(VALUE, zIndex.getPreValue(), DELTA);
    }

    @Test
    public void postValueTest() {
        zIndex.setPostValue(VALUE);

        assertEquals(VALUE, zIndex.getPostValue(), DELTA);
    }

}
