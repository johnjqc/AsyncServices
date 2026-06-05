package com.johnjqc.devsu.persona.domain.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long id) {
        super("Cliente no encontrado: " + id);
    }

    public ClientNotFoundException(String identification) {
        super("Cliente no encontrado: " + identification);
    }
}