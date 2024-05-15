package org.banco.service;

import org.banco.model.Cliente;
import org.banco.model.ClienteResponde;
import org.banco.model.CustomException;
import org.banco.repository.ClienteRepository;
import org.banco.repository.ClienteResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.banco.mapper.ClienteMapper.clienteToClienteResponde;
import static org.banco.model.ErrorCode.CLIENT_ALREADY_EXISTS;
import static org.banco.model.ErrorCode.CLIENT_NOT_FOUND;
import static org.banco.model.ErrorCode.DATABASE_ERROR;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteResponseRepository clienteRespondeRepository;

    @Autowired
    private PasswordService passwordService;

    public List<ClienteResponde> getAllClientes() {
        List<ClienteResponde> clientes = clienteRespondeRepository.findAll();
        if (clientes.isEmpty()) {
            throw new CustomException(CLIENT_NOT_FOUND);
        }
        return clientes;
    }

    public ClienteResponde getClienteById(Long id) {
        return clienteRespondeRepository.findById(id).orElseThrow(() -> new CustomException(CLIENT_NOT_FOUND));
    }

    public ClienteResponde getClienteByTipoIdentificacionAndIdentificacion(String tipoIdentificacion, String identificacion) {
        return clienteRespondeRepository.findByTipoIdentificacionAndIdentificacion(tipoIdentificacion, identificacion)
                .orElseThrow(() -> new CustomException(CLIENT_NOT_FOUND));
    }

    public ClienteResponde createCliente(Cliente cliente) {
        try {

            cliente.setContrasena(passwordService.hashPassword(cliente.getContrasena()));
            return clienteToClienteResponde(clienteRepository.save(cliente));
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getRootCause();
            if (cause != null && cause.getMessage().contains("ORA-00001")) {
                throw new CustomException(CLIENT_ALREADY_EXISTS);
            }
            throw new CustomException(DATABASE_ERROR);
        }
    }

    public ClienteResponde updateCliente(Cliente cliente) {
        return clienteRepository.findById(cliente.getClienteId())
                .map(existingCliente -> {
                    cliente.setContrasena(passwordService.hashNewPassword(cliente.getContrasena(), existingCliente.getContrasena()));
                    cliente.setFechaAlta(existingCliente.getFechaAlta());
                    cliente.setUsuarioAlta(existingCliente.getUsuarioAlta());
                    return clienteToClienteResponde(clienteRepository.save(cliente));
                })
                .orElseThrow(() -> new CustomException(CLIENT_NOT_FOUND));
    }

    public void deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new CustomException(CLIENT_NOT_FOUND);
        }
        clienteRepository.deleteById(id);
    }
}