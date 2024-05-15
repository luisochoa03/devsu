package org.banco.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.banco.model.Cliente;
import org.banco.model.ClienteResponde;
import org.banco.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponde>> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponde> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @GetMapping("/{tipoIdentificacion}/{identificacion}")
    public ResponseEntity<ClienteResponde> getClienteByTipoIdentificacionAndIdentificacion(@PathVariable String tipoIdentificacion, @PathVariable String identificacion) {
        ClienteResponde cliente = clienteService.getClienteByTipoIdentificacionAndIdentificacion(tipoIdentificacion, identificacion);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<ClienteResponde> createCliente(@RequestBody Cliente cliente, HttpServletRequest request) {
        String usuarioAlta = request.getHeader("user");
        cliente.setUsuarioAlta(usuarioAlta);
        return ResponseEntity.ok(clienteService.createCliente(cliente));
    }

    @PutMapping
    public ResponseEntity<ClienteResponde> updateCliente(@RequestBody Cliente cliente, HttpServletRequest request) {
        String usuarioModificacion = request.getHeader("user");
        cliente.setUsuarioModificacion(usuarioModificacion);
        return ResponseEntity.ok(clienteService.updateCliente(cliente));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok().build();
    }
}