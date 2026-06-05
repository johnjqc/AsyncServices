package com.johnjqc.devsu.cuenta.domain.service;

import com.johnjqc.devsu.cuenta.domain.model.Account;
import com.johnjqc.devsu.cuenta.domain.model.Transaction;
import com.johnjqc.devsu.cuenta.domain.exception.AccountNotFoundException;
import com.johnjqc.devsu.cuenta.domain.exception.BusinessException;
import com.johnjqc.devsu.cuenta.domain.exception.InsufficientBalanceException;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.repository.AccountJpaRepository;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.repository.TransactionJpaRepository;
import com.johnjqc.devsu.cuenta.domain.port.in.TransactionUseCase;
import com.johnjqc.devsu.cuenta.domain.service.dto.TransactionDto;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.mapper.TransactionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionUseCase {

    private final TransactionJpaRepository transactionJpaRepository;
    private final AccountJpaRepository accountJpaRepository;

    @Override
    @Transactional
    public TransactionDto create(TransactionDto dto) {

        Account account = accountJpaRepository.findById(dto.accountId())
                .orElseThrow(() -> new AccountNotFoundException(dto.accountId().toString()));

        if (dto.amount() == null || dto.amount().signum() <= 0) {
            throw new BusinessException("Amount must be greater than zero");
        }

        var currentBalance = account.getBalance();

        var normalizedAmount = switch (dto.transactionType()) {
            case "DEPOSIT" -> dto.amount();
            case "WITHDRAWAL" -> dto.amount().negate();
            default -> throw new IllegalStateException("Unexpected value: " + dto.transactionType());
        };

        var newBalance = account.getBalance().add(normalizedAmount);

        if (newBalance.signum() < 0) {
            throw new InsufficientBalanceException();
        }

        account.setBalance(newBalance);

        Transaction transaction = Transaction.builder()
                .account(account)
                .date(LocalDateTime.now())
                .transactionType(dto.transactionType())
                .amount(normalizedAmount)
                .status(true)
                .initialBalance(currentBalance)
                .availableBalance(newBalance)
                .build();

        accountJpaRepository.save(account);
        return TransactionMapper.toDto(transactionJpaRepository.save(transaction));
    }

    @Override
    public Page<TransactionDto> findByAccount(String accountNumber, Pageable pageable) {

        Page<Transaction> transactions = transactionJpaRepository
                .findByAccount_AccountNumber(
                        accountNumber,
                        pageable
                );

        return transactions.map(TransactionMapper::toDto);

    }
}
