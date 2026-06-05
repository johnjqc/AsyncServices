package com.johnjqc.devsu.cuenta.infrastructure.messaging.consumer;

import com.johnjqc.devsu.cuenta.infrastructure.persistence.entity.ClientSnapshotEntity;
import com.johnjqc.devsu.cuenta.domain.event.ClientCreatedEvent;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.repository.ClientSnapshotJpaRepository;
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

    private final ClientSnapshotJpaRepository repository;

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

        ClientSnapshotEntity snapshot = ClientSnapshotEntity.builder()
                .clientId(event.clientId())
                .name(event.name())
                .identification(event.identification()).build();

        repository.save(snapshot);
    }
}
