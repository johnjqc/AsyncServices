package com.johnjqc.devsu.cuenta.api.controller;

import com.johnjqc.devsu.cuenta.api.ReportsApi;
import com.johnjqc.devsu.cuenta.dto.AccountStatementReportResponse;
import com.johnjqc.devsu.cuenta.domain.port.in.ReportUseCase;
import com.johnjqc.devsu.cuenta.api.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportsApi {

    private final ReportUseCase reportUseCase;

    @Override
    public ResponseEntity<AccountStatementReportResponse> getReport(
            Long clientId, LocalDate fromDate, LocalDate toDate) {

        var report = reportUseCase.generateAccountStatement(
                clientId,
                fromDate,
                toDate
        );

        return ResponseEntity.ok(
                ReportMapper.toResponse(report)
        );
    }
}
