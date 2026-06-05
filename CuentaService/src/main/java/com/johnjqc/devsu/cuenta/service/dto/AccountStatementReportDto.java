package com.johnjqc.devsu.cuenta.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AccountStatementReportDto(

        Long clientId,

        String clientName,

        String identification,

        LocalDate fromDate,

        LocalDate toDate,

        LocalDateTime reportGeneratedAt,

        List<AccountStatementAccountDto> accounts
) {
}
