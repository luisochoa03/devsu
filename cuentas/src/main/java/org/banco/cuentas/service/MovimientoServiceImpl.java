package org.banco.cuentas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.banco.cuentas.model.Cuenta;
import org.banco.cuentas.model.CustomException;
import org.banco.cuentas.model.ErrorCode;
import org.banco.cuentas.model.Movimiento;
import org.banco.cuentas.model.ReporteRequest;
import org.banco.cuentas.repository.CuentaRepository;
import org.banco.cuentas.repository.MovimientoRepository;
import org.banco.cuentas.service.strategy.Operacion;
import org.banco.cuentas.service.strategy.OperacionCredito;
import org.banco.cuentas.service.strategy.OperacionDebito;
import org.banco.cuentas.util.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.banco.cuentas.util.DateUtil.convertStringsToDates;

@Transactional
@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Map<String, Operacion> operaciones = new HashMap<>();

    static  {
        operaciones.put("+", new OperacionCredito());
        operaciones.put("-", new OperacionDebito());
    }

    private BigDecimal ejecutarOperacion(String operacionCreditoDebito, BigDecimal saldo, BigDecimal valor) {
        Operacion operacion = operaciones.get(operacionCreditoDebito);
        if (operacion == null) {
            throw new CustomException(ErrorCode.INVALID_OPERATION);
        }
        return operacion.ejecutar(saldo, valor);
    }

    @Override
    public Movimiento createMovimiento(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getNumeroCuenta())
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        BigDecimal saldoActualizado = ejecutarOperacion(movimiento.getOperacionCreditoDebito(), cuenta.getSaldo(), movimiento.getValor());
        cuenta.setSaldo(saldoActualizado);
        cuentaRepository.save(cuenta);
        movimiento.setSaldo(saldoActualizado);
        movimiento.setCuenta(cuenta);
        return movimientoRepository.save(movimiento);
    }

    @Override
    public Movimiento updateMovimiento(Long id, Movimiento movimientoUpdate) {
        Movimiento movimientoOriginal = movimientoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.OPERATION_NOT_FOUND));
        Cuenta cuenta = cuentaRepository.findById(movimientoOriginal.getCuenta().getNumeroCuenta())
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        BigDecimal saldoActualizado = ejecutarOperacion(movimientoOriginal.getOperacionCreditoDebito(), cuenta.getSaldo(), movimientoOriginal.getValor().negate());
        saldoActualizado = ejecutarOperacion(movimientoUpdate.getOperacionCreditoDebito(), saldoActualizado, movimientoUpdate.getValor());
        cuenta.setSaldo(saldoActualizado);
        cuentaRepository.save(cuenta);
        movimientoOriginal.setFecha(movimientoUpdate.getFecha());
        movimientoOriginal.setTipoMovimiento(movimientoUpdate.getTipoMovimiento());
        movimientoOriginal.setValor(movimientoUpdate.getValor());
        movimientoOriginal.setOperacionCreditoDebito(movimientoUpdate.getOperacionCreditoDebito());
        movimientoOriginal.setSaldo(saldoActualizado);
        return movimientoRepository.save(movimientoOriginal);
    }

    @Override
    public void deleteMovimiento(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.OPERATION_NOT_FOUND));
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getNumeroCuenta())
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        BigDecimal saldoActualizado = ejecutarOperacion(movimiento.getOperacionCreditoDebito(), cuenta.getSaldo(), movimiento.getValor().negate());
        cuenta.setSaldo(saldoActualizado);
        cuentaRepository.save(cuenta);
        movimientoRepository.deleteById(id);
    }

    @Override
    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }


    @KafkaListener(topics = "generarEECCById", groupId = "CUENTA")
    public void manejarSolicitud(ConsumerRecord<Long, String> record) throws JsonProcessingException {
        String requestId = new String(record.headers().lastHeader(KafkaHeaders.CORRELATION_ID).value(), StandardCharsets.UTF_8);
        String value = record.value();

        ReporteRequest reporteRequest = objectMapper.readValue(value, ReporteRequest.class);

        List<Movimiento> movimientos = buscarMovimientosPorClienteIdYFecha(reporteRequest.getClienteId(), reporteRequest.getFechaInicio(), reporteRequest.getFechaFin());
        String s = objectMapper.writeValueAsString(movimientos);

        Message<String> message = MessageBuilder.withPayload(s)
                .setHeader(KafkaHeaders.CORRELATION_ID, requestId)
                .build();

        String payload = message.getPayload();

        kafkaTemplate.send("recibir-movimientos", requestId, payload);
    }
    @Override
    public List<Movimiento> buscarMovimientosPorClienteIdYFecha(Long clienteId, String fechaInicio, String fechaFin) {
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        DateRange dateRange = convertStringsToDates(fechaInicio, fechaFin);

        return movimientoRepository.findByCuentaInAndFechaBetween(cuentas,dateRange.startDate(),dateRange.endDate());
    }

}