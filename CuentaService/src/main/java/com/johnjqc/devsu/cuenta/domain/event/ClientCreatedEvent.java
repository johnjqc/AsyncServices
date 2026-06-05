package com.johnjqc.devsu.cuenta.domain.event;

public record ClientCreatedEvent(
        Long clientId,
        String name,
        String identification
) {}
