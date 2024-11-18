package org.arpha.exception.handler;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arpha.exception.EmailAlreadyTakenException;
import org.arpha.exception.InternalAuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.arpha.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ProblemDetail handleEmailAlreadyTakenException(EmailAlreadyTakenException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleEmailAlreadyTakenException(UserNotFoundException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class, AuthenticationException.class})
    public ProblemDetail handleAuthError(RuntimeException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler({InternalAuthorizationException.class})
    public ProblemDetail handleAuthError(InternalAuthorizationException e) {
        log.error(StringUtils.EMPTY, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(StringUtils.EMPTY, ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Some information isn't valid");
        problemDetail.setTitle("Constraint Violation");

        List<ConstraintErrorBody> constraintErrorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::createConstraintErrorBody)
                .toList();

        problemDetail.setProperties(Map.of("violations", constraintErrorList));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    private ConstraintErrorBody createConstraintErrorBody(ObjectError error) {
        if (error.contains(ConstraintViolation.class)) {
            ConstraintViolation<?> violation = error.unwrap(ConstraintViolation.class);
            return new ConstraintErrorBody(violation.getPropertyPath().toString(), violation.getMessage());
        } else {
            return new ConstraintErrorBody(error.getObjectName(), error.getDefaultMessage());
        }
    }

}
