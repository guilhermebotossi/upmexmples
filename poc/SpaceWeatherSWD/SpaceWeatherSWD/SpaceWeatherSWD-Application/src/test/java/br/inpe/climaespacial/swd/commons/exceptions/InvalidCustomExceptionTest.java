package br.inpe.climaespacial.swd.commons.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InvalidCustomExceptionTest {

    @Test
    public void constructor_called_returnsInstance() {
        String message = "Message";
        
        InvalidCustomException ice = new InvalidCustomException(message);
        
        assertEquals(message, ice.getMessage());
    }

}
