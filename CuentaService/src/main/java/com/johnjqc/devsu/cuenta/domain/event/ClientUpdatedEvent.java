package com.johnjqc.devsu.cuenta.domain.event;

public record ClientUpdatedEvent(
        Long clientId,
        String name,
        String identification
) {}
