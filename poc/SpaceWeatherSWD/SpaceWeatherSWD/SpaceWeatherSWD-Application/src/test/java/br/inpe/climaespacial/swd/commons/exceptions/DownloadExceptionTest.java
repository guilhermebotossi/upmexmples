package br.inpe.climaespacial.swd.commons.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DownloadExceptionTest {

    @Test
    public void constructor_called_returnsInstance() {
        String message = "Message";
        
        DownloadException de = new DownloadException(message);
        
        assertEquals(message, de.getMessage());
    }
    
}
