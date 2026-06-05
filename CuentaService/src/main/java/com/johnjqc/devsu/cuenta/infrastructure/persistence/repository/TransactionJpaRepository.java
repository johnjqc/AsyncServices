package com.johnjqc.devsu.cuenta.infrastructure.persistence.repository;

import com.johnjqc.devsu.cuenta.domain.model.Account;
import com.johnjqc.devsu.cuenta.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountAndDateBetween(
            Account account,
            LocalDateTime from,
            LocalDateTime to
    );

    Page<Transaction> findByAccount_AccountNumber(String accountNumber, Pageable pageable);
}
