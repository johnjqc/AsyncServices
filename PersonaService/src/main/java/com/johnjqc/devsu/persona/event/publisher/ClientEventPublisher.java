package com.johnjqc.devsu.persona.event.publisher;

import com.johnjqc.devsu.persona.event.payload.ClientCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientEventPublisher {

    private final KafkaTemplate<String, ClientCreatedEvent> kafkaTemplate;

    public void publishClientCreated(ClientCreatedEvent event) {
        kafkaTemplate.send("client.created", event.clientId().toString(), event);
    }
}
