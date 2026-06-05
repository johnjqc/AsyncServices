package com.johnjqc.devsu.cuenta.infrastructure.persistence.mapper;

import com.johnjqc.devsu.cuenta.domain.model.Transaction;
import com.johnjqc.devsu.cuenta.domain.service.dto.TransactionDto;

public class TransactionMapper {

    private TransactionMapper() {}

    public static TransactionDto toDto(Transaction t) {
        return new TransactionDto(
                t.getId(),
                t.getAccount().getId(),
                t.getDate(),
                t.getTransactionType(),
                t.getAmount(),
                t.getStatus(),
                t.getInitialBalance(),
                t.getAvailableBalance()
        );
    }
}
