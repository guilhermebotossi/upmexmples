package br.inpe.climaespacial.swd.commons.exceptions;

public class DownloadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DownloadException(String message) {
        super(message);
    }
    
}
