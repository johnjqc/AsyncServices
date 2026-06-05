package com.johnjqc.devsu.cuenta.service.mapper;

import com.johnjqc.devsu.cuenta.dto.AccountPageResponse;
import com.johnjqc.devsu.cuenta.dto.AccountRequest;
import com.johnjqc.devsu.cuenta.dto.AccountResponse;
import com.johnjqc.devsu.cuenta.entity.AccountType;
import com.johnjqc.devsu.cuenta.service.dto.AccountDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public class AccountApiMapper {

    public static AccountDto toDto(AccountRequest request) {

        if (request == null) {
            return null;
        }

        return new AccountDto(
                null,
                request.getAccountNumber(),
                AccountType.valueOf(request.getAccountType().name()),
                request.getInitialBalance() == null
                        ? null
                        : BigDecimal.valueOf(
                        request.getInitialBalance()
                ),
                request.getStatus(),
                request.getClientId().longValue()
        );
    }

    public static AccountResponse toResponse(AccountDto dto) {

        return new AccountResponse()
                .id(dto.id().intValue())
                .accountNumber(dto.accountNumber())
                .accountType(dto.accountType().name())
                .initialBalance(
                        dto.initialBalance() == null
                                ? null
                                : dto.initialBalance()
                )
                .status(dto.status())
                .clientId(dto.clientId().intValue());
    }

    public static AccountPageResponse toPageResponse(
            Page<AccountDto> page
    ) {

        var response = new AccountPageResponse();

        response.setContent(
                page.getContent()
                        .stream()
                        .map(AccountApiMapper::toResponse)
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
}
