package com.johnjqc.devsu.persona.infrastructure.messaging.publisher;

import com.johnjqc.devsu.persona.domain.event.ClientCreatedEvent;
import com.johnjqc.devsu.persona.domain.event.ClientUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaClientEventPublisher {

    private final KafkaTemplate<String, ClientCreatedEvent> kafkaTemplateClientCreated;
    private final KafkaTemplate<String, ClientUpdatedEvent> kafkaTemplateClientUpdated;

    public void publishClientCreated(ClientCreatedEvent event) {
        kafkaTemplateClientCreated.send("client.created", event.clientId().toString(), event);
    }

    public void publishClientUpdated(ClientUpdatedEvent event) {
        kafkaTemplateClientUpdated.send("client.updated", event.clientId().toString(), event);
    }
}
