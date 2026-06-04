package com.johnjqc.devsu.persona.exception;

public class ClientAlreadyExistsException extends RuntimeException {

    public ClientAlreadyExistsException(String identification) {
        super("Ya existe un cliente con identificación: " + identification);
    }
}