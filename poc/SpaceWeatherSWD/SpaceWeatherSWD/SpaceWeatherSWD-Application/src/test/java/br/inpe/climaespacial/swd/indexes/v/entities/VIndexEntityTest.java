package br.inpe.climaespacial.swd.indexes.v.entities;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(VIndexEntity.class)
public class VIndexEntityTest {

    private final double DELTA = 0.001;

    private final double VALUE = 1.0;

    @Inject
    private VIndexEntity vIndexEntity;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        vIndexEntity.setTimeTag(timeTag);

        assertEquals(timeTag, vIndexEntity.getTimeTag());
    }

    @Test
    public void vIndexPreValueTest() {
        vIndexEntity.setPreValue(VALUE);

        assertEquals(VALUE, vIndexEntity.getPreValue(), DELTA);
    }

    @Test
    public void vIndexPostValueTest() {
        vIndexEntity.setPostValue(VALUE);

        assertEquals(VALUE, vIndexEntity.getPostValue(), DELTA);
    }

    @Test
    public void isCycleBeginTest() {
        vIndexEntity.setIsCycleBegin(true);

        assertTrue(vIndexEntity.getIsCycleBegin());
    }

}
