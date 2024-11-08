package com.example.decafe.exception;

public class ImageNotFoundException extends Exception {
    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

