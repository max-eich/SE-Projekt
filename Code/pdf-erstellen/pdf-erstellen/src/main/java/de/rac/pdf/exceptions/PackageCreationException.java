package de.rac.pdf.exceptions;

public class PackageCreationException extends Exception {

    private final String message;

    public PackageCreationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
