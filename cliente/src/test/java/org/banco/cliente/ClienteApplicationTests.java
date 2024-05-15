package org.banco.cliente;

import org.banco.model.Cliente;
import org.banco.model.ClienteResponde;
import org.banco.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ClienteApplicationTests {

    @Autowired
    private ClienteService clienteService;

    @Test
    void contextLoads() {
    }

    @Test
    void clienteServiceIsAvailable() {
        assertNotNull(clienteService);
    }

    @Test
    @Transactional
    void createClienteTest() {
        Cliente cliente = new Cliente();
        cliente.setEmail("test@example.com");
        cliente.setContrasena("passwordD457@");
        cliente.setEstado(1);
        cliente.setDireccion("jr los lirios");
        cliente.setEdad(30);
        cliente.setGenero("M");
        cliente.setNombre("Pedro Jaramillo");
        cliente.setIdentificacion("587878888");
        cliente.setTipoIdentificacion("PASS");
        cliente.setTelefono("9898888888");
        cliente.setUsuarioAlta("admin");

        ClienteResponde createdCliente = clienteService.createCliente(cliente);

        assertNotNull(createdCliente);
        assertEquals(cliente.getEmail(), createdCliente.getEmail());
    }
}