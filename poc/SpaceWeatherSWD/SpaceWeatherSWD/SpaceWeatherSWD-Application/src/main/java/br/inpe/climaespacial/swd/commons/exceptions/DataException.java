package br.inpe.climaespacial.swd.commons.exceptions;

public class DataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

}
