package com.johnjqc.devsu.persona.event.payload;

public record ClientCreatedEvent(
        Long clientId,
        String name,
        String identification
) {}
