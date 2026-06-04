package com.johnjqc.devsu.cuenta.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        Long id,
        Long accountId,
        LocalDateTime date,
        String transactionType,
        BigDecimal amount,
        BigDecimal previousBalance,
        BigDecimal availableBalance,
        String description
) {}
