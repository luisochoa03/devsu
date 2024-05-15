package org.banco.eecc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.banco.eecc.dto.CuentaReporte;
import org.banco.eecc.dto.MovimientoReporte;
import org.banco.eecc.model.Movimiento;
import org.banco.eecc.dto.Reporte;
import org.banco.eecc.model.NoMovimientosException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteMapService {

    private final ObjectMapper objectMapper;

    public ReporteMapService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);

    }

    public Reporte build(String respuestaKafka) throws JsonProcessingException {
        List<Movimiento> movimientos = deserializeRespuestaKafka(respuestaKafka);

        String clienteId = null;
        Map<Long, List<Movimiento>> movimientosPorCuenta = null;
        if (!movimientos.isEmpty() && movimientos.get(0).getCuenta() != null) {
            clienteId = String.valueOf(movimientos.get(0).getCuenta().getClienteId());
            movimientosPorCuenta = groupMovimientosByCuenta(movimientos);
        } else {
            return new Reporte.Builder().buildEmtpty();
        }

        List<CuentaReporte> cuentas = buildCuentas(movimientosPorCuenta);

        return new Reporte.Builder()
                .withClienteId(Long.parseLong(clienteId))
                .withCuentas(cuentas)
                .build();
    }

    private List<Movimiento> deserializeRespuestaKafka(String respuestaKafka) throws JsonProcessingException {
        return objectMapper.readValue(respuestaKafka, new TypeReference<>(){});
    }

    private Map<Long, List<Movimiento>> groupMovimientosByCuenta(List<Movimiento> movimientos) {
        return movimientos.stream()
                .collect(Collectors.groupingBy(movimiento -> movimiento.getCuenta().getNumero()));
    }

    private List<CuentaReporte> buildCuentas(Map<Long, List<Movimiento>> movimientosPorCuenta) {
        List<CuentaReporte> cuentas = new ArrayList<>();
        for (Map.Entry<Long, List<Movimiento>> entry : movimientosPorCuenta.entrySet()) {
            CuentaReporte cuentaReporte = new CuentaReporte();
            cuentaReporte.setNumero(entry.getKey());
            List<MovimientoReporte> movimientoReportes = convertMovimientosToMovimientoReportes(entry.getValue());
            cuentaReporte.setMovimientos(movimientoReportes);
            cuentas.add(cuentaReporte);
        }

        return cuentas;
    }

    private List<MovimientoReporte> convertMovimientosToMovimientoReportes(List<Movimiento> movimientos) {

        return movimientos.stream()
                .map(movimiento -> new MovimientoReporte.Builder()
                        .withId(movimiento.getId())
                        .withFecha(movimiento.getFecha())
                        .withImporte(movimiento.getOperacionCreditoDebito(), movimiento.getValor().toString())
                        .withSaldo(String.valueOf(movimiento.getSaldo()))
                        .withTipo(movimiento.getTipo())
                        .build()).sorted(Comparator.comparing(MovimientoReporte::getId).reversed()).collect(Collectors.toList());
    }
}