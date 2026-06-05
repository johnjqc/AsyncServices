package com.johnjqc.devsu.cuenta.api.mapper;

import com.johnjqc.devsu.cuenta.dto.AccountPageResponse;
import com.johnjqc.devsu.cuenta.dto.AccountRequest;
import com.johnjqc.devsu.cuenta.dto.AccountResponse;
import com.johnjqc.devsu.cuenta.domain.model.AccountType;
import com.johnjqc.devsu.cuenta.domain.service.dto.AccountDto;
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
                request.getBalance() == null
                        ? null
                        : BigDecimal.valueOf(
                        request.getBalance()
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
                .balance(
                        dto.balance() == null
                                ? null
                                : dto.balance()
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
