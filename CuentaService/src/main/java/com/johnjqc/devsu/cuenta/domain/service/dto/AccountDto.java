package com.johnjqc.devsu.cuenta.domain.service.dto;

import com.johnjqc.devsu.cuenta.domain.model.AccountType;

import java.math.BigDecimal;

public record AccountDto(
        Long id,
        String accountNumber,
        AccountType accountType,
        BigDecimal balance,
        Boolean status,
        Long clientId
) {}
