package org.banco.eecc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.banco.eecc.dto.Reporte;
import org.banco.eecc.service.ReporteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/reportes")
    public CompletableFuture<Reporte> getReporte(@RequestParam("fechaInicio") String fechaInicio, @RequestParam("fechaFin") String fechaFin, @RequestParam("clientId") String clienteId) throws ExecutionException, InterruptedException, JsonProcessingException {
        return reporteService.generarReporte(fechaInicio, fechaFin, clienteId);
    }


    @PostMapping("/reportes")
    public void postReporte(@RequestParam("fechaInicio") String fechaInicio, @RequestParam("fechaFin") String fechaFin, @RequestParam("clientId") String clienteId, @RequestParam("emailSend") String emailSend) throws JsonProcessingException, ExecutionException, InterruptedException {
        reporteService.generarReporteEmail(fechaInicio, fechaFin, clienteId,emailSend);
    }
}