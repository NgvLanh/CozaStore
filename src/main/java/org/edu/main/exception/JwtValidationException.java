package org.edu.main.exception;

public class JwtValidationException extends RuntimeException{
    public JwtValidationException(String message) {
        super(message);
    }
}
