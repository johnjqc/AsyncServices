package com.johnjqc.devsu.cuenta.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "client_snapshot")
public class ClientSnapshot {

    @Id
    private Long clientId;

    private String name;

    private String identification;
}
