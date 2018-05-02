package br.inpe.climaespacial.swd.indexes.z.entities;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(ZIndexEntity.class)
public class ZIndexEntityTest {

    private final double DELTA = 0.001;

    private final double VALUE = 1.0;

    @Inject
    private ZIndexEntity zIndexEntity;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        zIndexEntity.setTimeTag(timeTag);

        assertEquals(timeTag, zIndexEntity.getTimeTag());
    }

    @Test
    public void preValueTest() {
        zIndexEntity.setPreValue(VALUE);

        assertEquals(VALUE, zIndexEntity.getPreValue(), DELTA);
    }

    
    @Test
    public void postValueTest() {
        zIndexEntity.setPostValue(VALUE);

        assertEquals(VALUE, zIndexEntity.getPostValue(), DELTA);
    }
}
