package com.johnjqc.devsu.cuenta.service.mapper;

import com.johnjqc.devsu.cuenta.dto.TransactionPageResponse;
import com.johnjqc.devsu.cuenta.dto.TransactionRequest;
import com.johnjqc.devsu.cuenta.dto.TransactionResponse;
import com.johnjqc.devsu.cuenta.service.dto.TransactionDto;
import org.springframework.data.domain.Page;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class TransactionApiMapper {

    public static TransactionDto toDto(TransactionRequest request) {

        if (request == null) {
            return null;
        }

        return new TransactionDto(
                null,
                request.getAccountId().longValue(),
                null,
                request.getTransactionType(),
                request.getAmount(),
                null,
                null,
                null
        );
    }

    public static TransactionResponse toResponse(
            TransactionDto dto
    ) {

        if (dto == null) {
            return null;
        }

        return new TransactionResponse()
                .id(dto.id().intValue())
                .accountId(dto.accountId().intValue())
                .date(toOffsetDateTime(dto.date()))
                .transactionType(
                        dto.transactionType()
                )
                .amount(
                        dto.amount() == null
                                ? null
                                : dto.amount()
                )
                .initialBalance(
                        dto.initialBalance() == null
                                ? null
                                : dto.initialBalance()
                )
                .availableBalance(
                        dto.availableBalance() == null
                                ? null
                                : dto.availableBalance()
                );
    }

    public static TransactionPageResponse toPageResponse(
            Page<TransactionDto> page
    ) {

        var response = new TransactionPageResponse();

        response.setContent(
                page.getContent()
                        .stream()
                        .map(TransactionApiMapper::toResponse)
                        .toList()
        );

        response.setTotalElements(
                page.getTotalElements()
        );

        response.setTotalPages(
                page.getTotalPages()
        );

        response.setSize(
                page.getSize()
        );

        response.setNumber(
                page.getNumber()
        );

        response.setFirst(
                page.isFirst()
        );

        response.setLast(
                page.isLast()
        );

        response.setEmpty(
                page.isEmpty()
        );

        return response;
    }

    private static OffsetDateTime toOffsetDateTime(
            java.time.LocalDateTime localDateTime) {

        return localDateTime == null
                ? null
                : localDateTime.atOffset(ZoneOffset.UTC);
    }
}
