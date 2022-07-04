package de.max.exception;

import de.max.Convert;

public class ConvertException extends Exception {

    private String message;

    public ConvertException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
