package com.johnjqc.devsu.persona.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ProblemDetail handleCustomerNotFound(ClientNotFoundException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Customer Not Found");
        problemDetail.setDetail(ex.getMessage());

        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        problemDetail.setProperty("errorCode", "CUSTOMER_NOT_FOUND");

        return problemDetail;
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ProblemDetail handleClientAlreadyExists(ClientAlreadyExistsException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        problemDetail.setTitle("Client Already Exists");
        problemDetail.setDetail(ex.getMessage());

        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        problemDetail.setProperty("errorCode", "CLIENT_ALREADY_EXISTS");

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {

        String details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Validation Error");
        problemDetail.setDetail(details);

        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        problemDetail.setProperty("errorCode", "VALIDATION_ERROR");

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(
            Exception exception
    ) {

        var problem =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        exception.getMessage()
                );

        problem.setTitle(
                "Unexpected Error"
        );

        return problem;
    }
}
