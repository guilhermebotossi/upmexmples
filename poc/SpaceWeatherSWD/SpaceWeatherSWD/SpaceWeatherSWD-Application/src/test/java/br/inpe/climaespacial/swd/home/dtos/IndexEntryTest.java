
package br.inpe.climaespacial.swd.home.dtos;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(IndexEntry.class)
public class IndexEntryTest {
    
    private static final double DELTA = 0.001;
    
    private static final double VALUE = 1.0;
    
    @Inject
    private IndexEntry indexEntry;
    
    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag  = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        indexEntry.setTimeTag(timeTag);

        assertSame(timeTag, indexEntry.getTimeTag());
    }

    @Test
    public void preValueTest() {
        indexEntry.setPreValue(VALUE);

        assertEquals(VALUE, indexEntry.getPreValue(), DELTA);
    }
    
    @Test
    public void postValueTest() {
        indexEntry.setPostValue(VALUE);
        
        assertEquals(VALUE, indexEntry.getPostValue(), DELTA);
    }

}
