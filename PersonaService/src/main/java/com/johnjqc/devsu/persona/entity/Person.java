package com.johnjqc.devsu.persona.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personas")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persona_id")
    private Long personId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false, length = 20)
    private Gender gender;

    @Column(name = "edad", nullable = false)
    private Integer age;

    @Column(name = "identificacion", nullable = false, length = 20)
    private String identification;

    @Column(name = "direccion", nullable = false, length = 255)
    private String address;

    @Column(name = "telefono", nullable = false, length = 20)
    private String phone;
}