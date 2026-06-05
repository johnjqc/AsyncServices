package com.johnjqc.devsu.cuenta.controller.advice;

import com.johnjqc.devsu.cuenta.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFound(
            AccountNotFoundException ex,
            HttpServletRequest request) {

        log.warn(
                "Account not found. path={}, message={}",
                request.getRequestURI(),
                ex.getMessage()
        );

        return buildProblem(
                HttpStatus.NOT_FOUND,
                "Account not found",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ProblemDetail handleTransactionNotFound(
            TransactionNotFoundException ex,
            HttpServletRequest request) {

        log.warn(
                "Transaction not found. path={}, message={}",
                request.getRequestURI(),
                ex.getMessage()
        );

        return buildProblem(
                HttpStatus.NOT_FOUND,
                "Transaction not found",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ProblemDetail handleClientNotFound(
            ClientNotFoundException ex,
            HttpServletRequest request) {

        log.warn(
                "Client not found. path={}, message={}",
                request.getRequestURI(),
                ex.getMessage()
        );

        return buildProblem(
                HttpStatus.NOT_FOUND,
                "Client not found",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleInsufficientBalance(
            InsufficientBalanceException ex,
            HttpServletRequest request) {

        log.warn(
                "Insufficient balance. path={}, message={}",
                request.getRequestURI(),
                ex.getMessage()
        );

        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Insufficient balance",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(
            BusinessException ex,
            HttpServletRequest request) {

        log.warn(
                "Business rule violation. path={}, message={}",
                request.getRequestURI(),
                ex.getMessage()
        );

        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Business rule violation",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        "%s: %s".formatted(
                                error.getField(),
                                error.getDefaultMessage()
                        ))
                .collect(Collectors.joining(", "));

        log.warn(
                "Validation error. path={}, errors={}",
                request.getRequestURI(),
                errors
        );

        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                errors,
                request
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        log.warn(
                "Constraint violation. path={}, message={}",
                request.getRequestURI(),
                ex.getMessage()
        );

        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedError(
            Exception ex,
            HttpServletRequest request) {

        log.error(
                "Unexpected error. path={}",
                request.getRequestURI(),
                ex
        );

        return buildProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                "An unexpected error occurred",
                request
        );
    }

    private ProblemDetail buildProblem(
            HttpStatus status,
            String title,
            String detail,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(status);

        problem.setTitle(title);
        problem.setDetail(detail);

        problem.setProperty(
                "timestamp",
                OffsetDateTime.now()
        );

        problem.setProperty(
                "path",
                request.getRequestURI()
        );

        return problem;
    }
}