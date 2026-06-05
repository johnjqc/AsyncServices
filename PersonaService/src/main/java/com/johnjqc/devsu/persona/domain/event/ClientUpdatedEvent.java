package com.johnjqc.devsu.persona.domain.event;

public record ClientUpdatedEvent(
        Long clientId,
        String name,
        String identification
) {}
