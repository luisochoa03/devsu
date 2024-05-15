package org.banco.cuentas.service;

import org.banco.cuentas.model.Cuenta;
import org.banco.cuentas.model.Movimiento;
import org.banco.cuentas.repository.CuentaRepository;
import org.banco.cuentas.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

 class MovimientoServiceImplTest {

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testCreateMovimiento() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(Long.valueOf("123456"));
        cuenta.setSaldo(new BigDecimal("1000"));

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setValor(new BigDecimal("100"));
        movimiento.setOperacionCreditoDebito("+");

        when(cuentaRepository.findById(any(Long.class))).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento);

        Movimiento resultado = movimientoService.createMovimiento(movimiento);

        assertEquals(movimiento, resultado, "El movimiento creado no es el esperado");
    }

     @Test
     void testUpdateMovimiento() {
         Cuenta cuenta = new Cuenta();
         cuenta.setNumeroCuenta(Long.valueOf("123456"));
         cuenta.setSaldo(new BigDecimal("1000"));

         Movimiento movimientoOriginal = new Movimiento();
         movimientoOriginal.setCuenta(cuenta);
         movimientoOriginal.setValor(new BigDecimal("100"));
         movimientoOriginal.setOperacionCreditoDebito("+");

         Movimiento movimientoUpdate = new Movimiento();
         movimientoUpdate.setCuenta(cuenta);
         movimientoUpdate.setValor(new BigDecimal("200"));
         movimientoUpdate.setOperacionCreditoDebito("-");

         when(cuentaRepository.findById(cuenta.getNumeroCuenta())).thenReturn(Optional.of(cuenta));
         when(movimientoRepository.findById(any(Long.class))).thenReturn(Optional.of(movimientoOriginal));
         when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimientoUpdate);

         Movimiento resultado = movimientoService.updateMovimiento(1L, movimientoUpdate);

         assertEquals(movimientoUpdate, resultado, "El movimiento actualizado no es el esperado");
     }
     @Test
     void testDeleteMovimiento() {
         // Crear datos de prueba
         Cuenta cuenta = new Cuenta();
         cuenta.setNumeroCuenta(Long.valueOf("123456"));
         cuenta.setSaldo(new BigDecimal("1000"));

         Movimiento movimiento = new Movimiento();
         movimiento.setCuenta(cuenta);
         movimiento.setValor(new BigDecimal("100"));
         movimiento.setOperacionCreditoDebito("+");

         when(cuentaRepository.findById(any(Long.class))).thenReturn(Optional.of(cuenta));
         when(movimientoRepository.findById(any(Long.class))).thenReturn(Optional.of(movimiento));

         // Llamar al método que se está probando
         movimientoService.deleteMovimiento(1L);

         // Verificar que el movimiento fue eliminado
         verify(movimientoRepository, times(1)).deleteById(1L);
     }

     @Test
     void testGetAllMovimientos() {
         Cuenta cuenta = new Cuenta();
         cuenta.setNumeroCuenta(Long.valueOf("123456"));
         cuenta.setSaldo(new BigDecimal("1000"));

         Movimiento movimiento1 = new Movimiento();
         movimiento1.setCuenta(cuenta);
         movimiento1.setValor(new BigDecimal("100"));
         movimiento1.setOperacionCreditoDebito("+");

         Movimiento movimiento2 = new Movimiento();
         movimiento2.setCuenta(cuenta);
         movimiento2.setValor(new BigDecimal("200"));
         movimiento2.setOperacionCreditoDebito("-");

         List<Movimiento> movimientos = Arrays.asList(movimiento1, movimiento2);

         when(movimientoRepository.findAll()).thenReturn(movimientos);

         List<Movimiento> resultado = movimientoService.getAllMovimientos();

         assertEquals(movimientos, resultado, "Los movimientos recuperados no son los esperados");
     }
}