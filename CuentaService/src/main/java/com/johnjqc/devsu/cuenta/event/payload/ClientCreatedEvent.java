package com.johnjqc.devsu.cuenta.event.payload;

public record ClientCreatedEvent(
        Long clientId,
        String name,
        String identification
) {}
