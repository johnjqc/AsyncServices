package com.johnjqc.devsu.cuenta.domain.port.in;

import com.johnjqc.devsu.cuenta.domain.service.dto.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountUseCase {

    AccountDto create(AccountDto dto);

    Page<AccountDto> findAccounts(Long clientId, Pageable pageable);

    AccountDto findByAccountNumber(String accountNumber);

    AccountDto update(String accountNumber, AccountDto dto);

}
