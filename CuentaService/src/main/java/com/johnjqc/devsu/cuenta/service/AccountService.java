package com.johnjqc.devsu.cuenta.service;

import com.johnjqc.devsu.cuenta.service.dto.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    AccountDto create(AccountDto dto);

    Page<AccountDto> findAccounts(Long clientId, Pageable pageable);

    AccountDto findByAccountNumber(String accountNumber);

    AccountDto update(String accountNumber, AccountDto dto);

}
