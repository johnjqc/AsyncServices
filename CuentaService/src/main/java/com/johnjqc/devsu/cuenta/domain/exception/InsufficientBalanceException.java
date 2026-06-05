package com.johnjqc.devsu.cuenta.domain.exception;

public class InsufficientBalanceException extends BusinessException {

    public InsufficientBalanceException() {
        super("Saldo no disponible");
    }
}
