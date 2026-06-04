package com.johnjqc.devsu.persona.service.dto;

import com.johnjqc.devsu.persona.entity.Gender;
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