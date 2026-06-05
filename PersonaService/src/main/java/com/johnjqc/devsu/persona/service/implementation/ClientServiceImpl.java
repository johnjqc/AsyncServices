package com.johnjqc.devsu.persona.service.implementation;

import com.johnjqc.devsu.persona.entity.Client;
import com.johnjqc.devsu.persona.entity.Gender;
import com.johnjqc.devsu.persona.entity.Person;
import com.johnjqc.devsu.persona.event.payload.ClientCreatedEvent;
import com.johnjqc.devsu.persona.event.payload.ClientUpdatedEvent;
import com.johnjqc.devsu.persona.event.publisher.ClientEventPublisher;
import com.johnjqc.devsu.persona.exception.ClientAlreadyExistsException;
import com.johnjqc.devsu.persona.exception.ClientNotFoundException;
import com.johnjqc.devsu.persona.mapper.ClientServiceMapper;
import com.johnjqc.devsu.persona.repository.ClientRepository;
import com.johnjqc.devsu.persona.repository.PersonRepository;
import com.johnjqc.devsu.persona.service.ClientService;
import com.johnjqc.devsu.persona.service.dto.ClientServiceRequest;
import com.johnjqc.devsu.persona.service.dto.ClientServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;
    private final ClientServiceMapper mapper;
    private final ClientEventPublisher clientEventPublisher;

    @Override
    @Transactional
    public ClientServiceResponse create(ClientServiceRequest request) {

        if (clientRepository.existsByPersonIdentification(request.identification())) {
            throw new ClientAlreadyExistsException(request.identification());
        }

        Client client = mapper.toEntity(request);

        Person savedPerson = personRepository.save(client.getPerson());
        client.setPerson(savedPerson);

        ClientServiceResponse clientServiceResponse = mapper.toServiceResponse(clientRepository.save(client));

        ClientCreatedEvent event = new ClientCreatedEvent(
                clientServiceResponse.clientId(),
                clientServiceResponse.name(),
                clientServiceResponse.identification()
        );

        clientEventPublisher.publishClientCreated(event);

        return clientServiceResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientServiceResponse findById(Long id) {

        return mapper.toServiceResponse(
                clientRepository.findById(id)
                        .orElseThrow(() -> new ClientNotFoundException(id))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientServiceResponse> findAll(Pageable pageable) {

        return clientRepository.findAll(pageable)
                .map(mapper::toServiceResponse);
    }

    @Override
    @Transactional
    public ClientServiceResponse update(Long id, ClientServiceRequest request) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        Person person = client.getPerson();

        person.setName(request.name());
        person.setGender(Gender.valueOf(request.gender().name()));
        person.setAge(request.age());
        person.setAddress(request.address());
        person.setPhone(request.phone());

        personRepository.save(person);

        client.setActive(request.active());

        ClientServiceResponse clientServiceResponse = mapper.toServiceResponse(clientRepository.save(client));

        ClientUpdatedEvent event = new ClientUpdatedEvent(
                clientServiceResponse.clientId(),
                clientServiceResponse.name(),
                clientServiceResponse.identification()
        );

        clientEventPublisher.publishClientUpdated(event);

        return clientServiceResponse;
    }

    @Override
    public void delete(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        clientRepository.delete(client);
        personRepository.delete(client.getPerson());
    }

    @Override
    @Transactional(readOnly = true)
    public ClientServiceResponse findByIdentification(String identification) {

        Client client = clientRepository.findByPersonIdentification(identification)
                .orElseThrow(() -> new ClientNotFoundException(identification));

        return mapper.toServiceResponse(client);
    }
}
