package com.johnjqc.devsu.persona.domain.service.dto;

import com.johnjqc.devsu.persona.dto.Gender;
import lombok.Builder;

@Builder
public record ClientServiceRequest(

        Long clientId,

        String name,

        Gender gender,

        Integer age,

        String identification,

        String address,

        String phone,

        String password,

        Boolean active

) {
}