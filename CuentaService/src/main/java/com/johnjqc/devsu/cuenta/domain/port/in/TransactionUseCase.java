package com.johnjqc.devsu.cuenta.domain.port.in;

import com.johnjqc.devsu.cuenta.domain.service.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionUseCase {

    TransactionDto create(TransactionDto dto);

    Page<TransactionDto> findByAccount(String accountNumber, Pageable pageable);
}
