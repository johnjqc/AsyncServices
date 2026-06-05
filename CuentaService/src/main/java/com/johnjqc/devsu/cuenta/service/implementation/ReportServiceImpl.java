package com.johnjqc.devsu.cuenta.service.implementation;

import com.johnjqc.devsu.cuenta.entity.Account;
import com.johnjqc.devsu.cuenta.entity.ClientSnapshot;
import com.johnjqc.devsu.cuenta.entity.Transaction;
import com.johnjqc.devsu.cuenta.exception.ClientNotFoundException;
import com.johnjqc.devsu.cuenta.repository.AccountRepository;
import com.johnjqc.devsu.cuenta.repository.ClientSnapshotRepository;
import com.johnjqc.devsu.cuenta.repository.TransactionRepository;
import com.johnjqc.devsu.cuenta.service.ReportService;
import com.johnjqc.devsu.cuenta.service.dto.AccountStatementAccountDto;
import com.johnjqc.devsu.cuenta.service.dto.AccountStatementReportDto;
import com.johnjqc.devsu.cuenta.service.dto.AccountStatementTransactionDto;
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
public class ReportServiceImpl implements ReportService {

    private final ClientSnapshotRepository clientSnapshotRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public AccountStatementReportDto generateAccountStatement(
            Long clientId, LocalDate fromDate, LocalDate toDate ) {

        ClientSnapshot client = clientSnapshotRepository
                .findById(clientId)
                .orElseThrow(
                        () -> new ClientNotFoundException(clientId)
                );

        var accounts = accountRepository.findByClientId(clientId);

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

        var transactions = transactionRepository
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
                account.getInitialBalance(),
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

    private BigDecimal calculateClosingBalance(
            Account account, List<Transaction> transactions) {

        return transactions.stream()
                .reduce(
                        (first, second) -> second
                )
                .map(Transaction::getAvailableBalance)
                .orElse(account.getInitialBalance());
    }
}
