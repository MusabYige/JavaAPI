package com.example.api.exception;

/**
 * Kullanıcı yetkisiz olduğunda atılacak özel istisna.
 */
public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
