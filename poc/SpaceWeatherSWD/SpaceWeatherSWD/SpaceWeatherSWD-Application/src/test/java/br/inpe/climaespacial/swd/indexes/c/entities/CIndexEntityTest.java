package br.inpe.climaespacial.swd.indexes.c.entities;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(CIndexEntity.class)
public class CIndexEntityTest {

    private final double DELTA = 0.001;

    private final double VALUE = 1.0;

    @Inject
    private CIndexEntity cIndexEntity;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        cIndexEntity.setTimeTag(timeTag);

        assertEquals(timeTag, cIndexEntity.getTimeTag());
    }

    @Test
    public void preValueTest() {
        cIndexEntity.setPreValue(VALUE);

        assertEquals(VALUE, cIndexEntity.getPreValue(), DELTA);
    }

    
    @Test
    public void postValueTest() {
        cIndexEntity.setPostValue(VALUE);

        assertEquals(VALUE, cIndexEntity.getPostValue(), DELTA);
    }

}
