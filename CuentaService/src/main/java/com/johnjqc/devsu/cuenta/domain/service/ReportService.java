package com.johnjqc.devsu.cuenta.domain.service;

import com.johnjqc.devsu.cuenta.domain.model.Account;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.entity.ClientSnapshotEntity;
import com.johnjqc.devsu.cuenta.domain.model.Transaction;
import com.johnjqc.devsu.cuenta.domain.exception.ClientNotFoundException;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.repository.AccountJpaRepository;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.repository.ClientSnapshotJpaRepository;
import com.johnjqc.devsu.cuenta.infrastructure.persistence.repository.TransactionJpaRepository;
import com.johnjqc.devsu.cuenta.domain.port.in.ReportUseCase;
import com.johnjqc.devsu.cuenta.domain.service.dto.AccountStatementAccountDto;
import com.johnjqc.devsu.cuenta.domain.service.dto.AccountStatementReportDto;
import com.johnjqc.devsu.cuenta.domain.service.dto.AccountStatementTransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService implements ReportUseCase {

    private final ClientSnapshotJpaRepository clientSnapshotJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public AccountStatementReportDto generateAccountStatement(
            Long clientId, LocalDate fromDate, LocalDate toDate ) {

        ClientSnapshotEntity client = clientSnapshotJpaRepository
                .findById(clientId)
                .orElseThrow(
                        () -> new ClientNotFoundException(clientId)
                );

        var accounts = accountJpaRepository.findByClientId(clientId);

        var accountResponses = accounts.stream()
                .map(account ->
                        buildAccountResponse(
                                account,
                                fromDate,
                                toDate
                        )
                )
                .toList();

        return new AccountStatementReportDto(
                client.getClientId(),
                client.getName(),
                client.getIdentification(),
                fromDate,
                toDate,
                LocalDateTime.now(),
                accountResponses
        );
    }

    private AccountStatementAccountDto buildAccountResponse(
            Account account, LocalDate fromDate, LocalDate toDate ) {

        var transactions = transactionJpaRepository
                .findByAccountAndDateBetween(
                        account,
                        fromDate.atStartOfDay(),
                        toDate.atTime(23, 59, 59)
                );

        var transactionResponses = transactions.stream()
                .map(this::buildTransactionResponse)
                .toList();

        return new AccountStatementAccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType().name(),
                account.getStatus(),
                account.getBalance(),
                calculateOpeningBalance(account, transactions),
                calculateClosingBalance(account, transactions),
                transactionResponses
        );
    }

    private AccountStatementTransactionDto buildTransactionResponse(
            Transaction transaction ) {

        return new AccountStatementTransactionDto(
                transaction.getId(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getInitialBalance(),
                transaction.getAvailableBalance()
        );
    }

    private BigDecimal calculateOpeningBalance(
            Account account,
            List<Transaction> transactions) {

        return transactions.stream()
                .findFirst()
                .map(Transaction::getInitialBalance)
                .orElse(account.getBalance());
    }

    private BigDecimal calculateClosingBalance(
            Account account, List<Transaction> transactions) {

        return transactions.stream()
                .reduce(
                        (first, second) -> second
                )
                .map(Transaction::getAvailableBalance)
                .orElse(account.getBalance());
    }
}
