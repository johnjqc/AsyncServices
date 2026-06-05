package com.johnjqc.devsu.persona.infrastructure.persistence.repository;

import com.johnjqc.devsu.persona.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJpaRepository extends JpaRepository<Person, Long> {

}
