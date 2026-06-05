package com.johnjqc.devsu.cuenta.event.consumer;

import com.johnjqc.devsu.cuenta.entity.ClientSnapshot;
import com.johnjqc.devsu.cuenta.event.payload.ClientUpdatedEvent;
import com.johnjqc.devsu.cuenta.repository.ClientSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientUpdatedConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ClientUpdatedConsumer.class);

    private final ClientSnapshotRepository repository;

    @KafkaListener(
            topics = "client.updated",
            groupId = "account-service"
    )
    public void consume(ClientUpdatedEvent event) {

        LOGGER.info(
                "Received CLIENT_UPDATED event. clientId={}, identification={}",
                event.clientId(),
                event.identification()
        );

        repository.findById(event.clientId())
                .ifPresentOrElse(
                        snapshot -> updateSnapshot(snapshot, event),
                        () -> createSnapshot(event)
                );
    }

    private void updateSnapshot(
            ClientSnapshot snapshot,
            ClientUpdatedEvent event
    ) {

        snapshot.setName(event.name());
        snapshot.setIdentification(event.identification());

        repository.save(snapshot);

        LOGGER.info("Client snapshot updated. clientId={}", event.clientId());
    }

    private void createSnapshot(ClientUpdatedEvent event) {

        ClientSnapshot snapshot = ClientSnapshot.builder()
                .clientId(event.clientId())
                .name(event.name())
                .identification(event.identification())
                .build();

        repository.save(snapshot);

        LOGGER.warn( "Snapshot not found. Created new snapshot. clientId={}", event.clientId());
    }
}
