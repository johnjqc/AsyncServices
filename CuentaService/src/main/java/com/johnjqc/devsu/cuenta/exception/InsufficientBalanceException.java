package com.johnjqc.devsu.cuenta.exception;

public class InsufficientBalanceException extends BusinessException {

    public InsufficientBalanceException() {
        super("Saldo no disponible");
    }
}
