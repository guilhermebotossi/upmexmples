package br.inpe.climaespacial.swd.commons.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArgumentExceptionTest {

    @Test
    public void constructor_called_returnsInstance() {
        String message = "Message";
        
        ArgumentException ae = new ArgumentException(message);
        
        assertEquals(message, ae.getMessage());
    }
    
}
