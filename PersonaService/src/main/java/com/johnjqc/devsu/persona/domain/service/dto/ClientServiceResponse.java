package com.johnjqc.devsu.persona.domain.service.dto;

import com.johnjqc.devsu.persona.domain.model.Gender;
import lombok.Builder;

@Builder
public record ClientServiceResponse(

        Long clientId,

        Long personId,

        String name,

        Gender gender,

        Integer age,

        String identification,

        String address,

        String phone,

        Boolean active

) {
}