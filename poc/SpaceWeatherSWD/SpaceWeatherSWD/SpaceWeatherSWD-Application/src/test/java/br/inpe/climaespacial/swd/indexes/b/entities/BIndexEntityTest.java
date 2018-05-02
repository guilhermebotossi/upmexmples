package br.inpe.climaespacial.swd.indexes.b.entities;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(BIndexEntity.class)
public class BIndexEntityTest {

    private final double DELTA = 0.001;

    private final double VALUE = 1.0;

    @Inject
    private BIndexEntity bIndexEntity;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        bIndexEntity.setTimeTag(timeTag);

        assertEquals(timeTag, bIndexEntity.getTimeTag());
    }
    
    @Test
    public void preValueTest() {
        bIndexEntity.setPreValue(VALUE);

        assertEquals(VALUE, bIndexEntity.getPreValue(), DELTA);
    }
    
    public void postValueTest() {
        bIndexEntity.setPostValue(VALUE);

        assertEquals(VALUE, bIndexEntity.getPostValue(), DELTA);
    }

}
