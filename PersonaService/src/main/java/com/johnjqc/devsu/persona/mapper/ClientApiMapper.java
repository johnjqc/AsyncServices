package com.johnjqc.devsu.persona.mapper;

import com.johnjqc.devsu.persona.dto.*;
import com.johnjqc.devsu.persona.service.dto.ClientServiceRequest;
import com.johnjqc.devsu.persona.service.dto.ClientServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ClientApiMapper {

    public ClientServiceRequest toServiceRequest(CreateCustomerRequest request) {

        return ClientServiceRequest.builder()
                .name(request.getName())
                .gender(request.getGender())
                .age(request.getAge())
                .identification(request.getIdentification())
                .address(request.getAddress())
                .phone(request.getPhone())
                .password(request.getPassword())
                .active(request.getActive())
                .build();
    }

    public ClientServiceRequest toServiceRequest(Long customerId, UpdateCustomerRequest request) {

        return ClientServiceRequest.builder()
                .clientId(customerId)
                .name(request.getName())
                .gender(request.getGender())
                .age(request.getAge())
                .address(request.getAddress())
                .phone(request.getPhone())
                .password(null) // TODO
                .active(request.getActive())
                .build();
    }

    public CustomerResponse toResponse(ClientServiceResponse response) {

        return new CustomerResponse()
                .customerId(response.clientId())
                .name(response.name())
                .gender(response.gender() != null ? Gender.fromValue(response.gender().name()) : null)
                .age(response.age())
                .identification(response.identification())
                .address(response.address())
                .phone(response.phone())
                .active(response.active());
                //.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
                //.updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(ZoneOffset.UTC) : null);

    }

    public CustomerPageResponse toPageResponse(Page<ClientServiceResponse> page) {

        return new CustomerPageResponse()
                .content(page.getContent().stream().map(this::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .numberOfElements(page.getNumberOfElements());
    }
}