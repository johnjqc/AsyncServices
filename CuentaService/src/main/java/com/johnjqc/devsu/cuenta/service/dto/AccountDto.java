package com.johnjqc.devsu.cuenta.service.dto;

import com.johnjqc.devsu.cuenta.entity.AccountType;

import java.math.BigDecimal;

public record AccountDto(
        Long id,
        String accountNumber,
        AccountType accountType,
        BigDecimal initialBalance,
        Boolean status,
        Long clientId
) {}
