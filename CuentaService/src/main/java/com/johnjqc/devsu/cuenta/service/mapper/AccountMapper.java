package com.johnjqc.devsu.cuenta.service.mapper;

import com.johnjqc.devsu.cuenta.entity.Account;
import com.johnjqc.devsu.cuenta.service.dto.AccountDto;

public class AccountMapper {

    private AccountMapper() {}

    public static AccountDto toDto(Account a) {
        return new AccountDto(
                a.getId(),
                a.getAccountNumber(),
                a.getAccountType(),
                a.getInitialBalance(),
                a.getStatus(),
                a.getClientId()
        );
    }

    public static Account toEntity(AccountDto dto) {
        Account a = new Account();
        a.setId(dto.id());
        a.setAccountNumber(dto.accountNumber());
        a.setAccountType(dto.accountType());
        a.setInitialBalance(dto.initialBalance());
        a.setStatus(dto.status());
        a.setClientId(dto.clientId());
        return a;
    }
}
