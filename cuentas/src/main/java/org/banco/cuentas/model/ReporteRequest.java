package org.banco.cuentas.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteRequest {
    private Long clienteId;
    private String fechaInicio;
    private String fechaFin;
}