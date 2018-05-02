package br.inpe.climaespacial.swd.commons.helpers;

public class ProperException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProperException(String message) {
        super(message);
    }
    
    public ProperException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
