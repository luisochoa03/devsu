package org.banco.cliente.model;

import org.banco.model.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteTest {

    @Test
    void testSetAndGetClienteId() {
        Cliente cliente = new Cliente();
        Long expectedClienteId = 1L;

        cliente.setClienteId(expectedClienteId);
        Long actualClienteId = cliente.getClienteId();

        assertEquals(expectedClienteId, actualClienteId);
    }

    @Test
    void testSetAndGetContrasena() {
        Cliente cliente = new Cliente();
        String expectedContrasena = "password";

        cliente.setContrasena(expectedContrasena);
        String actualContrasena = cliente.getContrasena();

        assertEquals(expectedContrasena, actualContrasena);
    }

    @Test
    void testSetAndGetEstado() {

        Cliente cliente = new Cliente();
        int expectedEstado = 1;

        cliente.setEstado(expectedEstado);
        int actualEstado = cliente.getEstado();

        assertEquals(expectedEstado, actualEstado);
    }
}