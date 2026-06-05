package com.johnjqc.devsu.cuenta.service;

import com.johnjqc.devsu.cuenta.service.dto.AccountStatementReportDto;

import java.time.LocalDate;

public interface ReportService {

    AccountStatementReportDto generateAccountStatement(
            Long clientId,
            LocalDate fromDate,
            LocalDate toDate
    );
}
