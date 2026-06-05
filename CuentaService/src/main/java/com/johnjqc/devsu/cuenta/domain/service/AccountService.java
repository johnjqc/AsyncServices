package com.johnjqc.devsu.cuenta.domain.service;

import com.johnjqc.devsu.cuenta.domain.model.Account;
import com.johnjqc.devsu.cuenta.domain.exception.AccountNotFoundException;
import com.johnjqc.devsu.cuenta.domain.exception.ClientNotFoundException;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.repository.ClientSnapshotJpaRepository;
import com.johnjqc.devsu.cuenta.domain.port.in.AccountUseCase;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.mapper.AccountMapper;
import com.johnjqc.devsu.cuenta.domain.service.dto.AccountDto;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountUseCase {

    private final AccountJpaRepository repository;
    private final ClientSnapshotJpaRepository clientSnapshotJpaRepository;

    @Override
    public AccountDto create(AccountDto dto) {
        clientSnapshotJpaRepository.findById(dto.clientId())
                .orElseThrow(() -> new ClientNotFoundException(dto.clientId()));

        Account saved = repository.save(AccountMapper.toEntity(dto));
        return AccountMapper.toDto(saved);
    }

    @Override
    public Page<AccountDto> findAccounts(Long clientId, Pageable pageable) {
        Page<Account> accounts = clientId == null
                ? repository.findAll(pageable)
                : repository.findByClientId(clientId, pageable);

        return accounts.map(AccountMapper::toDto);
    }

    @Override
    public AccountDto findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .map(AccountMapper::toDto)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Override
    public AccountDto update(String accountNumber, AccountDto dto) {
        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        account.setAccountType(dto.accountType());
        account.setStatus(dto.status());

        return AccountMapper.toDto(repository.save(account));
    }

}
