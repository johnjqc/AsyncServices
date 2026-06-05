package com.johnjqc.devsu.cuenta.service;

import com.johnjqc.devsu.cuenta.service.dto.AccountDto;
import com.johnjqc.devsu.cuenta.service.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    TransactionDto create(TransactionDto dto);

    Page<TransactionDto> findByAccount(String accountNumber, Pageable pageable);
}
