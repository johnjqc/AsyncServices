package com.johnjqc.devsu.cuenta.repository;

import com.johnjqc.devsu.cuenta.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByAccount_ClientId(Long clientId);

    List<Transaction> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Transaction> findByAccount_ClientIdAndDateBetween(
            Long clientId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    @Query("""
        SELECT t FROM Transaction t
        WHERE t.account.accountNumber = :accountNumber
    """)
    List<Transaction> findByAccountNumber(String accountNumber);
}
