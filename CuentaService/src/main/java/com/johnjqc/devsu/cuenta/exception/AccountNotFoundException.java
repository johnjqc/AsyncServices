package com.johnjqc.devsu.cuenta.exception;

public class AccountNotFoundException extends BusinessException {

    public AccountNotFoundException(String accountNumber) {
        super("Account not found: " + accountNumber);
    }
}
