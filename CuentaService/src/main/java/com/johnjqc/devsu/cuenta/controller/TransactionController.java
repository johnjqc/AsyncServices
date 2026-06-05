package com.johnjqc.devsu.cuenta.controller;

import com.johnjqc.devsu.cuenta.api.TransactionsApi;
import com.johnjqc.devsu.cuenta.dto.TransactionPageResponse;
import com.johnjqc.devsu.cuenta.dto.TransactionRequest;
import com.johnjqc.devsu.cuenta.dto.TransactionResponse;
import com.johnjqc.devsu.cuenta.service.TransactionService;
import com.johnjqc.devsu.cuenta.service.mapper.TransactionApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionsApi {

    private final TransactionService transactionService;

    @Override
    public ResponseEntity<TransactionResponse> createTransaction(TransactionRequest transactionRequest) {
        var transactionDto =
                TransactionApiMapper.toDto(transactionRequest);

        var created =
                transactionService.create(transactionDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        TransactionApiMapper.toResponse(created)
                );
    }

    @Override
    public ResponseEntity<TransactionPageResponse> getTransactionsByAccountId(String accountNumber, Integer page, Integer size) {
        var transactions = transactionService.findByAccount(
                        accountNumber,
                        PageRequest.of(page, size));

        return ResponseEntity.ok(
                TransactionApiMapper.toPageResponse(
                        transactions
                )
        );
    }
}
