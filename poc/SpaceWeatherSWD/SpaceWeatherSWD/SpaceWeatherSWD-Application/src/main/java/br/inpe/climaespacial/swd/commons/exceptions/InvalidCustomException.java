package br.inpe.climaespacial.swd.commons.exceptions;

public class InvalidCustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidCustomException(String message) {
        super(message);
    }

}
