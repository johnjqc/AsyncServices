package com.johnjqc.devsu.cuenta.service;

import com.johnjqc.devsu.cuenta.service.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    TransactionDto create(TransactionDto dto);

    List<TransactionDto> findByAccount(String accountNumber);
}
