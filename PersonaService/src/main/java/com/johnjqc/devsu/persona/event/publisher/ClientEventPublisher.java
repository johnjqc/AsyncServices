package com.johnjqc.devsu.persona.event.publisher;

import com.johnjqc.devsu.persona.event.payload.ClientCreatedEvent;
import com.johnjqc.devsu.persona.event.payload.ClientUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientEventPublisher {

    private final KafkaTemplate<String, ClientCreatedEvent> kafkaTemplateClientCreated;
    private final KafkaTemplate<String, ClientUpdatedEvent> kafkaTemplateClientUpdated;

    public void publishClientCreated(ClientCreatedEvent event) {
        kafkaTemplateClientCreated.send("client.created", event.clientId().toString(), event);
    }

    public void publishClientUpdated(ClientUpdatedEvent event) {
        kafkaTemplateClientUpdated.send("client.updated", event.clientId().toString(), event);
    }
}
