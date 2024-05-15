package org.banco.eecc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReporteRequest {

    @JsonProperty("clienteId")
    private String clienteId;

    @JsonProperty("fechaInicio")
    private String fechaInicio;

    @JsonProperty("fechaFin")
    private String fechaFin;

    private ReporteRequest() {}

    public static class Builder {
        private String clienteId;
        private String fechaInicio;
        private String fechaFin;

        public Builder withClienteId(String clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        public Builder withFechaInicio(String fechaInicio) {
            this.fechaInicio = fechaInicio;
            return this;
        }

        public Builder withFechaFin(String fechaFin) {
            this.fechaFin = fechaFin;
            return this;
        }

        public ReporteRequest build() {
            ReporteRequest reporteRequest = new ReporteRequest();
            reporteRequest.clienteId = this.clienteId;
            reporteRequest.fechaInicio = this.fechaInicio;
            reporteRequest.fechaFin = this.fechaFin;
            return reporteRequest;
        }
    }
}