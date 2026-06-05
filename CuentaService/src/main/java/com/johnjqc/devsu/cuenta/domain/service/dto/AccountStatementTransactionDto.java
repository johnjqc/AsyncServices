package com.johnjqc.devsu.cuenta.domain.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountStatementTransactionDto(

        Long transactionId,

        LocalDateTime date,

        BigDecimal amount,

        String transactionType,

        BigDecimal initialBalance,

        BigDecimal availableBalance
) {
}
