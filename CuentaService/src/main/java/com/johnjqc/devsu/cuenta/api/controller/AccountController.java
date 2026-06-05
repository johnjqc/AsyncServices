package com.johnjqc.devsu.cuenta.api.controller;

import com.johnjqc.devsu.cuenta.api.AccountsApi;
import com.johnjqc.devsu.cuenta.dto.AccountPageResponse;
import com.johnjqc.devsu.cuenta.dto.AccountRequest;
import com.johnjqc.devsu.cuenta.dto.AccountResponse;
import com.johnjqc.devsu.cuenta.domain.port.in.AccountUseCase;
import com.johnjqc.devsu.cuenta.domain.service.dto.AccountDto;
import com.johnjqc.devsu.cuenta.api.mapper.AccountApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountsApi {

    private final AccountUseCase accountUseCase;


    @Override
    public ResponseEntity<AccountResponse> createAccount(
            AccountRequest accountRequest) {

        AccountDto accountDto = AccountApiMapper.toDto(accountRequest);

        AccountDto created = accountUseCase.create(accountDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AccountApiMapper.toResponse(created));
    }

    @Override
    public ResponseEntity<AccountPageResponse> getAccounts(Long clientId, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<AccountDto> accounts =
                accountUseCase.findAccounts(
                        clientId, pageable
                );

        return ResponseEntity.ok(
                AccountApiMapper.toPageResponse(accounts)
        );
    }

    @Override
    public ResponseEntity<AccountResponse> getAccountById(String accountNumber) {
        AccountDto account =
                accountUseCase.findByAccountNumber(accountNumber);

        return ResponseEntity.ok(
                AccountApiMapper.toResponse(account)
        );
    }

    @Override
    public ResponseEntity<AccountResponse> updateAccounts(String accountNumber, AccountRequest accountRequest) {
        AccountDto accountDto =
                AccountApiMapper.toDto(accountRequest);

        AccountDto updated =
                accountUseCase.update(
                        accountNumber,
                        accountDto
                );

        return ResponseEntity.ok(
                AccountApiMapper.toResponse(updated)
        );
    }
}
