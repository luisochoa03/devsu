package org.banco.eecc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.banco.eecc.dto.Reporte;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ReporteService {
    CompletableFuture<Reporte> generarReporte(String fechaInicio, String fechaFin, String clienteId) throws ExecutionException, InterruptedException, JsonProcessingException;

    void generarReporteEmail(String fechaInicio, String fechaFin, String clienteId,String EmailSend) throws JsonProcessingException, ExecutionException, InterruptedException;

}