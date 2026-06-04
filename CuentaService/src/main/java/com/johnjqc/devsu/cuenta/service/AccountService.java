package com.johnjqc.devsu.cuenta.service;

import com.johnjqc.devsu.cuenta.service.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto create(Long clientId, AccountDto dto);

    AccountDto findByAccountNumber(String accountNumber);

    List<AccountDto> findByClientId(Long clientId);

    AccountDto update(String accountNumber, AccountDto dto);

    void delete(String accountNumber);
}
