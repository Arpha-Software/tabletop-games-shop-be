package org.arpha.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OrderServiceExceptionHandler {


    @ExceptionHandler(NovaPoshtaApiException.class)
    public ProblemDetail handleNovaPoshtaApiException(NovaPoshtaApiException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Exception happened when we tried " +
                                                                                  "to connect to Nova poshta servers");
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handleOrderNotFoundException(OrderNotFoundException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(CreateOrderException.class)
    public ProblemDetail handleCreateOrderException(CreateOrderException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(CreateConsignmentDocumentException.class)
    public ProblemDetail handleCreateConsignmentDocumentException(CreateConsignmentDocumentException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}
