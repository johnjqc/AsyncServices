package com.johnjqc.devsu.cuenta.service.implementation;

import com.johnjqc.devsu.cuenta.entity.Account;
import com.johnjqc.devsu.cuenta.entity.ClientSnapshot;
import com.johnjqc.devsu.cuenta.exception.AccountNotFoundException;
import com.johnjqc.devsu.cuenta.exception.ClientNotFoundException;
import com.johnjqc.devsu.cuenta.repository.ClientSnapshotRepository;
import com.johnjqc.devsu.cuenta.service.AccountService;
import com.johnjqc.devsu.cuenta.service.mapper.AccountMapper;
import com.johnjqc.devsu.cuenta.service.dto.AccountDto;
import com.johnjqc.devsu.cuenta.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final ClientSnapshotRepository clientSnapshotRepository;

    @Override
    public AccountDto create(Long clientId, AccountDto dto) {
        ClientSnapshot client = clientSnapshotRepository.findById(clientId)
                .orElseThrow(() ->
                        new ClientNotFoundException(clientId));
        Account saved = repository.save(AccountMapper.toEntity(dto));
        return AccountMapper.toDto(saved);
    }

    @Override
    public AccountDto findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .map(AccountMapper::toDto)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Override
    public List<AccountDto> findByClientId(Long clientId) {
        return repository.findByClientId(clientId)
                .stream()
                .map(AccountMapper::toDto)
                .toList(); // Java 21 feature
    }

    @Override
    public AccountDto update(String accountNumber, AccountDto dto) {
        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        account.setAccountType(dto.accountType());
        account.setStatus(dto.status());

        return AccountMapper.toDto(repository.save(account));
    }

    @Override
    public void delete(String accountNumber) {
        repository.findByAccountNumber(accountNumber)
                .ifPresent(repository::delete);
    }
}
