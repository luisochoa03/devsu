package org.banco.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@MappedSuperclass
@Getter
@Setter
public class Persona {
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "genero")
    private String genero;

    @Column(name = "edad")
    private int edad;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    @Column(name = "identificacion")
    private String identificacion;

    @Column(name = "email")
    private String email;

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