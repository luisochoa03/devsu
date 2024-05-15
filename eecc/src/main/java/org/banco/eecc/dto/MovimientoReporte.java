package org.banco.eecc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimientoReporte {

    @JsonProperty("numero de operacion")
    private Long id;

    @JsonProperty("fecha movimiento")
    private String fecha;
    @JsonProperty("saldo actual")
    private String saldo;

    @JsonProperty("importe")
    private String importe;

    @JsonProperty("operacion")
    private String tipo;

    private MovimientoReporte() {}


    public static class Builder {
        private Long id;
        private String fecha;
        private String saldo;
        private String importe;
        private String tipo;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withFecha(String fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder withSaldo(String saldo) {
            this.saldo = saldo;
            return this;
        }

        public Builder withImporte(String operacion, String monto) {
            this.importe = operacion.equals("-") ? "-" + monto : monto;
            return this;
        }

        public Builder withTipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public MovimientoReporte build() {
            MovimientoReporte movimientoReporte = new MovimientoReporte();
            movimientoReporte.id = this.id;
            movimientoReporte.fecha = this.fecha;
            movimientoReporte.saldo = this.saldo;
            movimientoReporte.importe = this.importe;
            movimientoReporte.tipo = this.tipo;
            return movimientoReporte;
        }
    }
}