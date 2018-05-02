package br.inpe.climaespacial.swd.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultCollectionUtils.class)
public class CollectionUtilsTest {

    @Inject
    private CollectionUtils collectionUtils;

    @Test
    public void reverse_calledWithNull_throws() {
        RuntimeException re = null;
        try {
            collectionUtils.reverse(null);
        } catch(RuntimeException e) {
            re = e; 
        }
        
        assertNotNull(re);
        assertEquals("Argument \"list\" cannot be null.", re.getMessage());
    }
    
    @Test
    public void reverse_called_succeeds() {
        List<Long> il = Arrays.asList(1L,2L,3L);
        
        collectionUtils.reverse(il);
        
        assertEquals(Long.valueOf(3), il.get(0));
        assertEquals(Long.valueOf(2), il.get(1));
        assertEquals(Long.valueOf(1), il.get(2));
    }
}
