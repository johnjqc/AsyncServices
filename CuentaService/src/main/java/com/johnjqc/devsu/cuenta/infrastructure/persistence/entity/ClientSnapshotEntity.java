package com.johnjqc.devsu.cuenta.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_snapshot")
public class ClientSnapshotEntity {

    @Id
    private Long clientId;

    private String name;

    private String identification;
}
