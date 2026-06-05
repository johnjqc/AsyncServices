package com.johnjqc.devsu.cuenta.service.mapper;

import com.johnjqc.devsu.cuenta.dto.AccountStatementAccountResponse;
import com.johnjqc.devsu.cuenta.dto.AccountStatementReportResponse;
import com.johnjqc.devsu.cuenta.dto.AccountStatementTransactionResponse;
import com.johnjqc.devsu.cuenta.service.dto.AccountStatementAccountDto;
import com.johnjqc.devsu.cuenta.service.dto.AccountStatementReportDto;
import com.johnjqc.devsu.cuenta.service.dto.AccountStatementTransactionDto;

import java.time.OffsetDateTime;

public final class ReportMapper {

    private ReportMapper() {}

    public static AccountStatementReportResponse toResponse(AccountStatementReportDto dto) {
        return new AccountStatementReportResponse()
                .clientId(dto.clientId())
                .clientName(dto.clientName())
                .identification(dto.identification())
                .fromDate(dto.fromDate())
                .toDate(dto.toDate())
                .reportGeneratedAt(dto.reportGeneratedAt().atOffset(OffsetDateTime.now().getOffset()))
                .accounts(dto.accounts()
                        .stream()
                        .map(ReportMapper::toAccountResponse)
                        .toList());
    }

    public static AccountStatementAccountResponse toAccountResponse(AccountStatementAccountDto dto) {

        return new AccountStatementAccountResponse()
                .accountId(dto.accountId())
                .accountNumber(dto.accountNumber())
                .accountType(dto.accountType())
                .status(dto.status())
                .openingBalance(dto.openingBalance().doubleValue())
                .closingBalance(dto.closingBalance().doubleValue())
                .transactions(dto.transactions()
                        .stream()
                        .map(ReportMapper::toTransactionResponse)
                        .toList());
    }

    public static AccountStatementTransactionResponse toTransactionResponse(AccountStatementTransactionDto dto) {

        return new AccountStatementTransactionResponse()
                .transactionId(dto.transactionId())
                .date(dto.date().atOffset(OffsetDateTime.now().getOffset()))
                .amount(dto.amount().doubleValue())
                .transactionType(dto.transactionType())
                .initialBalance(dto.initialBalance().doubleValue())
                .availableBalance(dto.availableBalance().doubleValue());
    }
}
