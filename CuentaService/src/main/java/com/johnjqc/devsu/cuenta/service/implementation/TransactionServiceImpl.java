package com.johnjqc.devsu.cuenta.service.implementation;

import com.johnjqc.devsu.cuenta.entity.Account;
import com.johnjqc.devsu.cuenta.entity.Transaction;
import com.johnjqc.devsu.cuenta.exception.AccountNotFoundException;
import com.johnjqc.devsu.cuenta.exception.InsufficientBalanceException;
import com.johnjqc.devsu.cuenta.repository.AccountRepository;
import com.johnjqc.devsu.cuenta.repository.TransactionRepository;
import com.johnjqc.devsu.cuenta.service.TransactionService;
import com.johnjqc.devsu.cuenta.service.dto.AccountDto;
import com.johnjqc.devsu.cuenta.service.dto.TransactionDto;
import com.johnjqc.devsu.cuenta.service.mapper.AccountMapper;
import com.johnjqc.devsu.cuenta.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public TransactionDto create(TransactionDto dto) {

        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new AccountNotFoundException(dto.accountId().toString()));

        var newBalance = account.getInitialBalance().add(dto.initialBalance());

        if (newBalance.signum() < 0) {
            throw new InsufficientBalanceException();
        }

        account.setInitialBalance(newBalance);

        Transaction transaction = Transaction.builder()
                .account(account)
                .date(LocalDateTime.now())
                .transactionType(dto.transactionType())
                .amount(dto.amount())
                .status(true)
                .initialBalance(account.getInitialBalance())
                .availableBalance(newBalance)
                .build();

        accountRepository.save(account);
        return TransactionMapper.toDto(transactionRepository.save(transaction));
    }

    @Override
    public Page<TransactionDto> findByAccount(String accountNumber, Pageable pageable) {

        Page<Transaction> transactions = transactionRepository
                .findByAccount_AccountNumber(
                        accountNumber,
                        pageable
                );

        return transactions.map(TransactionMapper::toDto);

    }
}
