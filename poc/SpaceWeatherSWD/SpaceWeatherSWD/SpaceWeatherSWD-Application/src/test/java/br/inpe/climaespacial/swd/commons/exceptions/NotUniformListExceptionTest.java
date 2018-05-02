package br.inpe.climaespacial.swd.commons.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NotUniformListExceptionTest {

    @Test
    public void constructor_called_returnsInstance() {
        String message = "Message";
        
        NotUniformListException nule = new NotUniformListException(message);
        
        assertEquals(message, nule.getMessage());
    }
    
}
