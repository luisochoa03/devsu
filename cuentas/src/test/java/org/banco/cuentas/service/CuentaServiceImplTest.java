package org.banco.cuentas.service;

import org.banco.cuentas.model.Cuenta;
import org.banco.cuentas.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllCuentas() {
        Cuenta cuenta1 = new Cuenta();
        Cuenta cuenta2 = new Cuenta();
        List<Cuenta> cuentas = Arrays.asList(cuenta1, cuenta2);

        when(cuentaRepository.findAll()).thenReturn(cuentas);

        List<Cuenta> resultado = cuentaService.getAllCuentas();

        assertEquals(cuentas, resultado, "Las cuentas recuperadas no son las esperadas");
    }

    @Test
    void testCreateCuenta() {
        Cuenta cuenta = new Cuenta();

        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        Cuenta resultado = cuentaService.createCuenta(cuenta);

        assertEquals(cuenta, resultado, "La cuenta creada no es la esperada");
    }

    @Test
    void testUpdateCuenta() {
        Cuenta existingCuenta = new Cuenta();
        Cuenta cuentaDetails = new Cuenta();

        when(cuentaRepository.findById(any(Long.class))).thenReturn(Optional.of(existingCuenta));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuentaDetails);

        Cuenta resultado = cuentaService.updateCuenta(1L, cuentaDetails);

        assertEquals(cuentaDetails, resultado, "La cuenta actualizada no es la esperada");
    }

    @Test
    void testDeleteCuenta() {
        when(cuentaRepository.existsById(any(Long.class))).thenReturn(true);
        cuentaService.deleteCuenta(1L);
        verify(cuentaRepository, times(1)).deleteById(1L);
    }
}