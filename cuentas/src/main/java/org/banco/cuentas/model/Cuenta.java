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
@Table(name = "Cuenta")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_cuenta")
    private Long numeroCuenta;

    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "fecha_alta")
    private OffsetDateTime fechaAlta;

    @Column(name = "usuario_alta")
    private String usuarioAlta;

    @Column(name = "fecha_modificacion")
    private OffsetDateTime fechaModificacion;

    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;

    @PrePersist
    protected void prePersist() {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        this.fechaAlta = utc;
    }

    @PreUpdate
    protected void preUpdate() {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        this.fechaModificacion = utc;
    }
}