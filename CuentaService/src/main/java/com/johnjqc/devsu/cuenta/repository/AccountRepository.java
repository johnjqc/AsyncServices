package com.johnjqc.devsu.cuenta.repository;

import com.johnjqc.devsu.cuenta.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByClientId(Long clientId);

    Page<Account> findByClientId(Long clientId, Pageable pageable);

    boolean existsByAccountNumber(String accountNumber);
}
