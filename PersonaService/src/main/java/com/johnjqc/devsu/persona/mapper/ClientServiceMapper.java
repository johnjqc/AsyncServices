package com.johnjqc.devsu.persona.mapper;

import com.johnjqc.devsu.persona.entity.Client;
import com.johnjqc.devsu.persona.entity.Gender;
import com.johnjqc.devsu.persona.entity.Person;
import com.johnjqc.devsu.persona.service.dto.ClientServiceRequest;
import com.johnjqc.devsu.persona.service.dto.ClientServiceResponse;
import org.springframework.stereotype.Component;

@Component
public class ClientServiceMapper {

    public Client toEntity(ClientServiceRequest request) {

        Person person = Person.builder()
                .name(request.name())
                .gender(Gender.valueOf(request.gender().name()))
                .age(request.age())
                .identification(request.identification())
                .address(request.address())
                .phone(request.phone())
                .build();

        return Client.builder()
                .clientId(request.clientId())
                .person(person)
                .password(request.password())
                .active(request.active())
                .build();
    }

    public ClientServiceResponse toServiceResponse(Client client) {

        return ClientServiceResponse.builder()
                .clientId(client.getClientId())
                .personId(client.getPerson().getPersonId())
                .name(client.getPerson().getName())
                .gender(client.getPerson().getGender())
                .age(client.getPerson().getAge())
                .identification(client.getPerson().getIdentification())
                .address(client.getPerson().getAddress())
                .phone(client.getPerson().getPhone())
                .active(client.getActive())
                .build();
    }
}