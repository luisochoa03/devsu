package org.banco.eecc.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CuentaReporte {

    @JsonProperty("numero de Cuenta")
    private Long numero;

    private List<MovimientoReporte> movimientos;
}