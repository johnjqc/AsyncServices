package com.johnjqc.devsu.cuenta.event.consumer;

import com.johnjqc.devsu.cuenta.entity.ClientSnapshot;
import com.johnjqc.devsu.cuenta.event.payload.ClientCreatedEvent;
import com.johnjqc.devsu.cuenta.repository.ClientSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientCreatedConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ClientCreatedConsumer.class);

    private final ClientSnapshotRepository repository;

    @KafkaListener(
            topics = "client.created",
            groupId = "account-service"
    )
    public void consume(ClientCreatedEvent event) {

        LOGGER.info(
                "Received CLIENT_CREATED event. clientId={}, identification={}",
                event.clientId(),
                event.identification()
        );

        if (repository.existsById(event.clientId())) {
            LOGGER.warn(
                    "Client snapshot already exists. Ignoring duplicate event. clientId={}",
                    event.clientId()
            );
            return;
        }

        ClientSnapshot snapshot = ClientSnapshot.builder()
                .clientId(event.clientId())
                .name(event.name())
                .identification(event.identification()).build();

        repository.save(snapshot);
    }
}
