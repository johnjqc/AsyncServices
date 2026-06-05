package com.johnjqc.devsu.cuenta.domain.exception;

public class TransactionNotFoundException extends BusinessException {

    public TransactionNotFoundException() {
        super("Transacción no disponible");
    }
}