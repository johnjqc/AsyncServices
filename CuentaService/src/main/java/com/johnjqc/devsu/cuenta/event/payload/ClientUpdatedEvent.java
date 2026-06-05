package com.johnjqc.devsu.cuenta.event.payload;

public record ClientUpdatedEvent(
        Long clientId,
        String name,
        String identification
) {}
