package com.johnjqc.devsu.cuenta.exception;

public class TransactionNotFoundException extends BusinessException {

    public TransactionNotFoundException() {
        super("Transacción no disponible");
    }
}