package com.johnjqc.devsu.persona.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long clientId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "persona_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_cliente_persona")
    )
    private Person person;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String password;

    @Column(name = "estado", nullable = false)
    private Boolean active;
}