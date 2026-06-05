package com.johnjqc.devsu.cuenta.domain.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        Long id,
        Long accountId,
        LocalDateTime date,
        String transactionType,
        BigDecimal amount,
        Boolean status,
        BigDecimal initialBalance,
        BigDecimal availableBalance
) {}
