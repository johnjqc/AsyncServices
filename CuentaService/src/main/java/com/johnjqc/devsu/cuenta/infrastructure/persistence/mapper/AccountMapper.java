package com.johnjqc.devsu.cuenta.infrastructure.persistence.mapper;

import com.johnjqc.devsu.cuenta.domain.model.Account;
import com.johnjqc.devsu.cuenta.domain.service.dto.AccountDto;

public class AccountMapper {

    private AccountMapper() {}

    public static AccountDto toDto(Account a) {
        return new AccountDto(
                a.getId(),
                a.getAccountNumber(),
                a.getAccountType(),
                a.getBalance(),
                a.getStatus(),
                a.getClientId()
        );
    }

    public static Account toEntity(AccountDto dto) {
        Account a = new Account();
        a.setId(dto.id());
        a.setAccountNumber(dto.accountNumber());
        a.setAccountType(dto.accountType());
        a.setBalance(dto.balance());
        a.setStatus(dto.status());
        a.setClientId(dto.clientId());
        return a;
    }
}
