package org.banco.repository;

import org.banco.model.ClienteResponde;

import java.util.Optional;

public interface ClienteResponseRepository extends BaseRepository<ClienteResponde, Long> {
    Optional<ClienteResponde> findByTipoIdentificacionAndIdentificacion(String tipoIdentificacion, String identificacion);

}