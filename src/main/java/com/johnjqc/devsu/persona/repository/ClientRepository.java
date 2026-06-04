package com.johnjqc.devsu.persona.repository;

import com.johnjqc.devsu.persona.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByPersonIdentification(String identification);

    boolean existsByPersonIdentification(String identification);
}
