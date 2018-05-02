package br.inpe.climaespacial.swd.commons.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DataExceptionTest {

    @Test
    public void constructor_called_returnsInstance() {
        RuntimeException re = new RuntimeException();
        
        String message = "Message";
        
        DataException de = new DataException(message, re);
        
        assertEquals(message, de.getMessage());
        
        assertEquals(re, de.getCause());
    }
    
}
