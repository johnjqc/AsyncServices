package com.johnjqc.devsu.persona.repository;

import com.johnjqc.devsu.persona.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByIdentification(String identification);

    boolean existsByIdentification(String identification);
}
