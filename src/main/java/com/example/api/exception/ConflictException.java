package com.example.api.exception;

/**
 * Veri çatışması durumunda atılacak özel istisna.
 */
public class ConflictException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConflictException(String message) {
        super(message);
    }
}
