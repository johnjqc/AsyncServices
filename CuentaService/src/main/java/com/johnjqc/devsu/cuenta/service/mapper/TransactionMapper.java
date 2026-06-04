package com.johnjqc.devsu.cuenta.service.mapper;

import com.johnjqc.devsu.cuenta.entity.Transaction;
import com.johnjqc.devsu.cuenta.service.dto.TransactionDto;

public class TransactionMapper {

    public static TransactionDto toDto(Transaction t) {
        return new TransactionDto(
                t.getId(),
                t.getAccount().getId(),
                t.getDate(),
                t.getTransactionType(),
                t.getAmount(),
                t.getPreviousBalance(),
                t.getAvailableBalance(),
                t.getDescription()
        );
    }
}
