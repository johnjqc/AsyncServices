package com.johnjqc.devsu.cuenta.controller.advice;

import com.johnjqc.devsu.cuenta.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFound(
            AccountNotFoundException ex,
            HttpServletRequest request) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problem.setTitle("Account not found");
        problem.setDetail(ex.getMessage());

        problem.setProperty("timestamp", OffsetDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ProblemDetail handleTransactionNotFound(
            TransactionNotFoundException ex,
            HttpServletRequest request) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problem.setTitle("Transaction not found");
        problem.setDetail(ex.getMessage());

        problem.setProperty("timestamp", OffsetDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ProblemDetail handleClientNotFound(
            ClientNotFoundException ex,
            HttpServletRequest request) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problem.setTitle("Client not found");
        problem.setDetail(ex.getMessage());

        problem.setProperty("timestamp", OffsetDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleInsufficientBalance(
            InsufficientBalanceException ex,
            HttpServletRequest request) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Insufficient balance");
        problem.setDetail(ex.getMessage());

        problem.setProperty("timestamp", OffsetDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(
            BusinessException ex,
            HttpServletRequest request) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Business rule violation");
        problem.setDetail(ex.getMessage());

        problem.setProperty("timestamp", OffsetDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Validation error");
        problem.setDetail(errors);

        problem.setProperty("timestamp", OffsetDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Validation error");
        problem.setDetail(ex.getMessage());

        problem.setProperty("timestamp", OffsetDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedError(
            Exception ex,
            HttpServletRequest request) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("Internal server error");
        problem.setDetail(
                "An unexpected error occurred"
        );

        problem.setProperty("timestamp", OffsetDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }
}
