package com.johnjqc.devsu.cuenta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Account account;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime date;

    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private String transactionType;

    @Column(name = "valor", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "estado", nullable = false)
    private Boolean status;

    @Column(name = "saldo_inicial", nullable = false, precision = 18, scale = 2)
    private BigDecimal initialBalance;

    @Column(name = "saldo_disponible", nullable = false, precision = 18, scale = 2)
    private BigDecimal availableBalance;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDateTime.now();
        }

        if (status == null) {
            status = Boolean.TRUE;
        }
    }
}
