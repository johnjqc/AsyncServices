package com.johnjqc.devsu.cuenta.domain.port.in;

import com.johnjqc.devsu.cuenta.domain.service.dto.AccountStatementReportDto;

import java.time.LocalDate;

public interface ReportUseCase {

    AccountStatementReportDto generateAccountStatement(
            Long clientId,
            LocalDate fromDate,
            LocalDate toDate
    );
}
