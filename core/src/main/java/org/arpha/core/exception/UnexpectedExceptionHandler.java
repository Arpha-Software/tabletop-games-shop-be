package org.arpha.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order
@RestControllerAdvice
@Slf4j
public class UnexpectedExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedExceptions(Exception e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error happened, we are working on it!");
    }

}
