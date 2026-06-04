package com.johnjqc.devsu.cuenta.exception;

public class ClientNotFoundException extends BusinessException {

    public ClientNotFoundException(Long clientId) {
        super("Cliente no encontrado: " + clientId);
    }
}