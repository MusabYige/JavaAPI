package com.example.api.exception;

/**
 * Kullanıcının erişimi yasaklandığında atılacak özel istisna.
 */
public class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbiddenException(String message) {
        super(message);
    }
}
