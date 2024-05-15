package org.banco.eecc.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.banco.eecc.dto.Email;
import org.banco.eecc.dto.Reporte;
import org.banco.eecc.dto.ReporteRequest;
import org.banco.eecc.model.NoMovimientosException;
import org.banco.eecc.service.ReporteMapService;
import org.banco.eecc.service.ReporteService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import static org.banco.eecc.dto.ReporteToByteConverter.convert;


@Service
public class ReporteServiceImpl implements ReporteService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Map<String, CompletableFuture<Reporte>> futures = new ConcurrentHashMap<>();

    public ReporteServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public CompletableFuture<Reporte> generarReporte(String fechaInicio, String fechaFin, String clienteId) throws JsonProcessingException {
        return enviarMensajeFuture(fechaInicio, fechaFin, clienteId);
    }

    @Override
    public void generarReporteEmail(String fechaInicio, String fechaFin, String clienteId, String emailSend) throws JsonProcessingException, ExecutionException, InterruptedException {
        CompletableFuture<Reporte> future = enviarMensajeFuture(fechaInicio, fechaFin, clienteId);
        future.thenAccept(reporte -> {
            try {
                enviarMensaje(new Email.Builder()
                        .withTo(emailSend)
                        .withSubject("Tu Banco - Te enviando tu EECC Solicitado")
                        .withBody("Hola, te enviamo tu EECC")
                        .withAttachment(convert(reporte))
                        .withNameAttachment(String.format("EECC-%s-%s-%s.txt", fechaInicio, fechaFin, clienteId))
                        .build());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).get();

    }

    private void enviarMensaje(Email email) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(email);

        Message<String> message = MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, "comunication-topic")
                .build();

        kafkaTemplate.send(message);


    }

    private CompletableFuture<Reporte> enviarMensajeFuture(String fechaInicio, String fechaFin, String clienteId) throws JsonProcessingException {
        String requestId = UUID.randomUUID().toString();
        ReporteRequest reporteRequest = new ReporteRequest.Builder()
                .withClienteId(clienteId)
                .withFechaInicio(fechaInicio)
                .withFechaFin(fechaFin)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(reporteRequest);

        Message<String> message = MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, "generarEECCById")
                .setHeader(KafkaHeaders.CORRELATION_ID, requestId)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<Reporte> future = new CompletableFuture<>();
        futures.put(requestId, future);

        return future;
    }

    @KafkaListener(topics = "recibir-movimientos", groupId = "EECC")
    public void listen(@Headers Map<String, Object> headers, String message) throws JsonProcessingException {
        String requestId = (String) headers.get(KafkaHeaders.RECEIVED_KEY);
        ReporteMapService reporteMapService = new ReporteMapService();
        Reporte reporte = reporteMapService.build(message);

        CompletableFuture<Reporte> future = futures.remove(requestId);
        if (future != null) {
            future.complete(reporte);
        }

        if(reporte == null){
            throw new NoMovimientosException("No se encontraron movimientos para el cliente");
        }
    }
}