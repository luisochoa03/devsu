package org.banco.eecc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Movimiento {

    @JsonProperty("movimientoId")
    private Long id;

    private String fecha;

    @JsonProperty("tipoMovimiento")
    private String tipo;

    @JsonProperty("operacionCreditoDebito")
    private String operacionCreditoDebito;

    private Double valor;


    private Double saldo;

    private Cuenta cuenta;

}