package org.arpha.exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException() {
    }

    public FileNotFoundException(String message) {
        super(message);
    }

}