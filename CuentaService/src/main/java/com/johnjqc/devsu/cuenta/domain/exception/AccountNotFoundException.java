package com.johnjqc.devsu.cuenta.domain.exception;

public class AccountNotFoundException extends BusinessException {

    public AccountNotFoundException(String accountNumber) {
        super("Account not found: " + accountNumber);
    }
}
