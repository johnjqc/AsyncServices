package com.johnjqc.devsu.cuenta.service.implementation;

import com.johnjqc.devsu.cuenta.entity.Account;
import com.johnjqc.devsu.cuenta.exception.AccountNotFoundException;
import com.johnjqc.devsu.cuenta.exception.ClientNotFoundException;
import com.johnjqc.devsu.cuenta.repository.ClientSnapshotRepository;
import com.johnjqc.devsu.cuenta.service.AccountService;
import com.johnjqc.devsu.cuenta.service.mapper.AccountMapper;
import com.johnjqc.devsu.cuenta.service.dto.AccountDto;
import com.johnjqc.devsu.cuenta.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final ClientSnapshotRepository clientSnapshotRepository;

    @Override
    public AccountDto create(AccountDto dto) {
        clientSnapshotRepository.findById(dto.clientId())
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
