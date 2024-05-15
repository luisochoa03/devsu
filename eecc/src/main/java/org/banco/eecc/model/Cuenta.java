package org.banco.eecc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cuenta {

    @JsonProperty("numeroCuenta")
    private Long numero;

    @JsonProperty("tipoCuenta")
    private String tipo;

    private Double saldo;

    private Integer estado;

    @JsonProperty("clienteId")
    private Long clienteId;

    @JsonProperty("fechaAlta")
    private String fechaAlta;

    @JsonProperty("usuarioAlta")
    private String usuarioAlta;

    @JsonProperty("fechaModificacion")
    private String fechaModificacion;

    @JsonProperty("usuarioModificacion")
    private String usuarioModificacion;

}