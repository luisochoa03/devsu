package org.banco.eecc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Reporte {

    @JsonProperty("id del cliente")
    private Long clienteId;

    private List<CuentaReporte> cuentas;

    public static class Builder {
        private Long clienteId;
        private List<CuentaReporte> cuentas;

        public Builder withClienteId(Long clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        public Builder withCuentas(List<CuentaReporte> cuentas) {
            this.cuentas = cuentas;
            return this;
        }

        public Reporte build() {
            Reporte reporte = new Reporte();
            reporte.setClienteId(this.clienteId);
            reporte.setCuentas(this.cuentas);
            return reporte;
        }


        public Reporte buildEmtpty() {
            return null;
        }
    }
}