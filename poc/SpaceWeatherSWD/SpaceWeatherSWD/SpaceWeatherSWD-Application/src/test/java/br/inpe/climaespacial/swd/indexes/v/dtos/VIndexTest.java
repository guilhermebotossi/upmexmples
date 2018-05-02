package br.inpe.climaespacial.swd.indexes.v.dtos;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class VIndexTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private VIndex vIndex;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        vIndex.setTimeTag(timeTag);

        assertEquals(timeTag, vIndex.getTimeTag());
    }

    @Test
    public void preValueTest() {
        vIndex.setPreValue(VALUE);

        assertEquals(VALUE, vIndex.getPreValue(), DELTA);
    }

    @Test
    public void postValueTest() {
        vIndex.setPostValue(VALUE);

        assertEquals(VALUE, vIndex.getPostValue(), DELTA);
    }
    
    @Test
    public void isCycleBeginTest() {
        vIndex.setIsCycleBegin(true);

        assertTrue(vIndex.getIsCycleBegin());
    }

}
