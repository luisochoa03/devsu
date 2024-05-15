package org.banco.cuentas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.banco.cuentas.model.Movimiento;

import java.util.List;

public interface MovimientoService {
    List<Movimiento> getAllMovimientos();

    Movimiento createMovimiento(Movimiento movimiento);

    Movimiento updateMovimiento(Long id, Movimiento movimientoDetails);

    void deleteMovimiento(Long id);

    List<Movimiento> buscarMovimientosPorClienteIdYFecha(Long clienteId,String fechaInicio, String fechaFin);

}