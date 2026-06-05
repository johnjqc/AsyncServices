package com.johnjqc.devsu.persona.event.payload;

public record ClientUpdatedEvent(
        Long clientId,
        String name,
        String identification
) {}
