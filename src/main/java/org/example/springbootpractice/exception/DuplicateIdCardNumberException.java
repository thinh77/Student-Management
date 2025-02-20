package org.example.springbootpractice.exception;

public class DuplicateIdCardNumberException extends RuntimeException {
    public DuplicateIdCardNumberException(String message) {
        super(message);
    }
}
