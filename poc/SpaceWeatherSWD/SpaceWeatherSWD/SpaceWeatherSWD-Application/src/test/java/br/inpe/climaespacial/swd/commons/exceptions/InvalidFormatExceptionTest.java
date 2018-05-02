package br.inpe.climaespacial.swd.commons.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InvalidFormatExceptionTest {

    @Test
    public void constructor_called_returnsInstance() {
        String message = "Message";
        
        InvalidFormatException ife = new InvalidFormatException(message);
        
        assertEquals(message, ife.getMessage());
    }
    
}
