package org.arpha.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arpha.exception.FileNotFoundException;
import org.arpha.exception.FileUploadException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MediaExceptionHandler {

    @Order(95)
    @ExceptionHandler(FileUploadException.class)
    public ProblemDetail handleFileUploadException(FileUploadException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @Order(96)
    @ExceptionHandler(FileNotFoundException.class)
    public ProblemDetail handleFileNotFoundException(FileNotFoundException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }
}

