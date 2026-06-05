package com.johnjqc.devsu.persona.domain.event;

public record ClientCreatedEvent(
        Long clientId,
        String name,
        String identification
) {}
