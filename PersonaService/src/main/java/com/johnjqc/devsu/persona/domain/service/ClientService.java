package com.johnjqc.devsu.persona.domain.service;

import com.johnjqc.devsu.persona.domain.model.Client;
import com.johnjqc.devsu.persona.domain.model.Gender;
import com.johnjqc.devsu.persona.domain.model.Person;
import com.johnjqc.devsu.persona.domain.event.ClientCreatedEvent;
import com.johnjqc.devsu.persona.domain.event.ClientUpdatedEvent;
import com.johnjqc.devsu.persona.infrastructure.messaging.publisher.KafkaClientEventPublisher;
import com.johnjqc.devsu.persona.domain.exception.ClientAlreadyExistsException;
import com.johnjqc.devsu.persona.domain.exception.ClientNotFoundException;
import com.johnjqc.devsu.persona.infrastructure.persistence.mapper.ClientPersistenceMapper;
import com.johnjqc.devsu.persona.infrastructure.persistence.repository.ClientJpaRepository;
import com.johnjqc.devsu.persona.infrastructure.persistence.repository.PersonJpaRepository;
import com.johnjqc.devsu.persona.domain.port.in.ClientUseCase;
import com.johnjqc.devsu.persona.domain.service.dto.ClientServiceRequest;
import com.johnjqc.devsu.persona.domain.service.dto.ClientServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService implements ClientUseCase {

    private final ClientJpaRepository clientJpaRepository;
    private final PersonJpaRepository personJpaRepository;
    private final ClientPersistenceMapper mapper;
    private final KafkaClientEventPublisher kafkaClientEventPublisher;

    @Override
    @Transactional
    public ClientServiceResponse create(ClientServiceRequest request) {

        if (clientJpaRepository.existsByPersonIdentification(request.identification())) {
            throw new ClientAlreadyExistsException(request.identification());
        }

        Client client = mapper.toEntity(request);

        Person savedPerson = personJpaRepository.save(client.getPerson());
        client.setPerson(savedPerson);

        ClientServiceResponse clientServiceResponse = mapper.toServiceResponse(clientJpaRepository.save(client));

        ClientCreatedEvent event = new ClientCreatedEvent(
                clientServiceResponse.clientId(),
                clientServiceResponse.name(),
                clientServiceResponse.identification()
        );

        kafkaClientEventPublisher.publishClientCreated(event);

        return clientServiceResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientServiceResponse findById(Long id) {

        return mapper.toServiceResponse(
                clientJpaRepository.findById(id)
                        .orElseThrow(() -> new ClientNotFoundException(id))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientServiceResponse> findAll(Pageable pageable) {

        return clientJpaRepository.findAll(pageable)
                .map(mapper::toServiceResponse);
    }

    @Override
    @Transactional
    public ClientServiceResponse update(Long id, ClientServiceRequest request) {

        Client client = clientJpaRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        Person person = client.getPerson();

        person.setName(request.name());
        person.setGender(Gender.valueOf(request.gender().name()));
        person.setAge(request.age());
        person.setAddress(request.address());
        person.setPhone(request.phone());

        personJpaRepository.save(person);

        client.setActive(request.active());

        ClientServiceResponse clientServiceResponse = mapper.toServiceResponse(clientJpaRepository.save(client));

        ClientUpdatedEvent event = new ClientUpdatedEvent(
                clientServiceResponse.clientId(),
                clientServiceResponse.name(),
                clientServiceResponse.identification()
        );

        kafkaClientEventPublisher.publishClientUpdated(event);

        return clientServiceResponse;
    }

    @Override
    public void delete(Long id) {

        Client client = clientJpaRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        clientJpaRepository.delete(client);
        personJpaRepository.delete(client.getPerson());
    }

    @Override
    @Transactional(readOnly = true)
    public ClientServiceResponse findByIdentification(String identification) {

        Client client = clientJpaRepository.findByPersonIdentification(identification)
                .orElseThrow(() -> new ClientNotFoundException(identification));

        return mapper.toServiceResponse(client);
    }
}
