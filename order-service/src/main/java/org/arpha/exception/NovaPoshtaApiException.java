package org.arpha.exception;

public class NovaPoshtaApiException extends RuntimeException {

    public NovaPoshtaApiException(String message) {
        super(message);
    }

    public NovaPoshtaApiException() {
        super();
    }
}
