package org.banco.mapper;

import org.banco.model.Cliente;
import org.banco.model.ClienteResponde;


public class ClienteMapper {

    public static ClienteResponde clienteToClienteResponde(Cliente cliente) {
        ClienteResponde clienteResponde = new ClienteResponde();


        clienteResponde.setClienteId(cliente.getClienteId());
        clienteResponde.setNombre(cliente.getNombre());
        clienteResponde.setTipoIdentificacion(cliente.getTipoIdentificacion());
        clienteResponde.setIdentificacion(cliente.getIdentificacion());
        clienteResponde.setFechaAlta(cliente.getFechaAlta());
        clienteResponde.setUsuarioAlta(cliente.getUsuarioAlta());
        clienteResponde.setDireccion(cliente.getDireccion());
        clienteResponde.setTelefono(cliente.getTelefono());
        clienteResponde.setEmail(cliente.getEmail());
        return clienteResponde;
    }
}