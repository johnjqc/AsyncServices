package com.johnjqc.devsu.persona.infrastructure.persistence.repository;

import com.johnjqc.devsu.persona.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientJpaRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByPersonIdentification(String identification);

    boolean existsByPersonIdentification(String identification);
}
