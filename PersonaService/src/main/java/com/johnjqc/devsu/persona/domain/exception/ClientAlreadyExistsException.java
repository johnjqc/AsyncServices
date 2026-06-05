package com.johnjqc.devsu.persona.domain.exception;

public class ClientAlreadyExistsException extends RuntimeException {

    public ClientAlreadyExistsException(String identification) {
        super("Ya existe un cliente con identificación: " + identification);
    }
}