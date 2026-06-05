package com.johnjqc.devsu.cuenta.controller;

import com.johnjqc.devsu.cuenta.api.ReportsApi;
import com.johnjqc.devsu.cuenta.dto.AccountStatementReportResponse;
import com.johnjqc.devsu.cuenta.service.ReportService;
import com.johnjqc.devsu.cuenta.service.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportsApi {

    private final ReportService reportService;

    @Override
    public ResponseEntity<AccountStatementReportResponse> getReport(
            Long clientId, LocalDate fromDate, LocalDate toDate) {

        var report = reportService.generateAccountStatement(
                clientId,
                fromDate,
                toDate
        );

        return ResponseEntity.ok(
                ReportMapper.toResponse(report)
        );
    }
}
