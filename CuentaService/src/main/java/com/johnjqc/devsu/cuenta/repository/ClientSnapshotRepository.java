package com.johnjqc.devsu.cuenta.repository;

import com.johnjqc.devsu.cuenta.entity.ClientSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientSnapshotRepository extends JpaRepository<ClientSnapshot, Long> {

    Optional<ClientSnapshot> findByIdentification(String identification);

    boolean existsByIdentification(String identification);
}
