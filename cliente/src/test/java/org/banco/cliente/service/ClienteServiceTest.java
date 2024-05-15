package org.banco.cliente.service;

import org.banco.model.Cliente;
import org.banco.model.ClienteResponde;
import org.banco.repository.ClienteResponseRepository;
import org.banco.repository.ClienteRepository;
import org.banco.service.ClienteService;
import org.banco.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

 class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteResponseRepository clienteRespondeRepository;

    @Mock
    private PasswordService passwordService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetAllClientes() {
        ClienteResponde clienteResponde = new ClienteResponde();
        List<ClienteResponde> expectedClientes = Collections.singletonList(clienteResponde);
        when(clienteRespondeRepository.findAll()).thenReturn(expectedClientes);

        List<ClienteResponde> actualClientes = clienteService.getAllClientes();

        assertEquals(expectedClientes, actualClientes);
    }

     @Test
      void testGetClienteById() {
          Long id = 1L;
         ClienteResponde clienteResponde = new ClienteResponde();
         when(clienteRespondeRepository.findById(id)).thenReturn(Optional.of(clienteResponde));

          ClienteResponde actualCliente = clienteService.getClienteById(id);

          assertEquals(clienteResponde, actualCliente);
     }

     @Test
      void testGetClienteByTipoIdentificacionAndIdentificacion() {
          String tipoIdentificacion = "CC";
         String identificacion = "123456";
         ClienteResponde clienteResponde = new ClienteResponde();
         when(clienteRespondeRepository.findByTipoIdentificacionAndIdentificacion(tipoIdentificacion, identificacion)).thenReturn(Optional.of(clienteResponde));

          ClienteResponde actualCliente = clienteService.getClienteByTipoIdentificacionAndIdentificacion(tipoIdentificacion, identificacion);

          assertEquals(clienteResponde, actualCliente);
     }

     @Test
     void testCreateCliente() {
          Cliente cliente = mock(Cliente.class);
         ClienteResponde clienteResponde = new ClienteResponde();
         clienteResponde.setEmail("test@example.com"); // Establece el correo electrónico de clienteResponde
         when(cliente.getClienteId()).thenReturn(1L);
         when(cliente.getContrasena()).thenReturn("password");
         when(clienteRepository.findById(cliente.getClienteId())).thenReturn(Optional.of(cliente));
         when(passwordService.hashNewPassword(cliente.getContrasena(), cliente.getContrasena())).thenReturn("hashedPassword");
         when(clienteRepository.save(cliente)).thenReturn(cliente);
         when(cliente.getEmail()).thenReturn("test@example.com");

          ClienteResponde actualClienteResponde = clienteService.createCliente(cliente);

          assertEquals(clienteResponde.getEmail(), actualClienteResponde.getEmail());
     }

     @Test
     void testUpdateCliente() {
          Cliente cliente = mock(Cliente.class);
         ClienteResponde clienteResponde = new ClienteResponde();
         clienteResponde.setEmail("test@example.com"); // Establece el correo electrónico de clienteResponde
         when(cliente.getClienteId()).thenReturn(1L);
         when(cliente.getContrasena()).thenReturn("password");
         when(clienteRepository.findById(cliente.getClienteId())).thenReturn(Optional.of(cliente));
         when(passwordService.hashNewPassword(cliente.getContrasena(), cliente.getContrasena())).thenReturn("hashedPassword");
         when(clienteRepository.save(cliente)).thenReturn(cliente);
         when(cliente.getEmail()).thenReturn("test@example.com");

          ClienteResponde actualClienteResponde = clienteService.updateCliente(cliente);

          assertEquals(clienteResponde.getEmail(), actualClienteResponde.getEmail());
     }

     @Test
      void testDeleteCliente() {
          Long id = 1L;
         when(clienteRepository.existsById(id)).thenReturn(true);

          clienteService.deleteCliente(id);

          verify(clienteRepository, times(1)).deleteById(id);
     }
}