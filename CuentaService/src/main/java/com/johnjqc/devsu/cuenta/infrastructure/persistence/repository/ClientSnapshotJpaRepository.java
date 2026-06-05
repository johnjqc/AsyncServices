package com.johnjqc.devsu.cuenta.infrastructure.persistence.repository;

import com.johnjqc.devsu.cuenta.infrastructure.persistence.entity.ClientSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientSnapshotJpaRepository extends JpaRepository<ClientSnapshotEntity, Long> {

}
