package com.johnjqc.devsu.cuenta.service.dto;

import java.math.BigDecimal;
import java.util.List;

public record AccountStatementAccountDto(

        Long accountId,

        String accountNumber,

        String accountType,

        Boolean status,

        BigDecimal balance,

        BigDecimal openingBalance,

        BigDecimal closingBalance,

        List<AccountStatementTransactionDto> transactions
) {
}
