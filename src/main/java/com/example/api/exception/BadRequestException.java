package com.example.api.exception;

/**
 * Geçersiz istek olduğunda atılacak özel istisna.
 */
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}
