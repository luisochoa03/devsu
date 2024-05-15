package org.banco.cuentas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "Movimiento")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Long movimientoId;

    @Column(name = "fecha")
    private OffsetDateTime fecha;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    @Column(name = "operacion_credito_debito")
    private String operacionCreditoDebito;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "numero_cuenta", nullable = false)
    private Cuenta cuenta;

    @PrePersist
    protected void prePersist() {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        this.fecha = utc;
    }
}