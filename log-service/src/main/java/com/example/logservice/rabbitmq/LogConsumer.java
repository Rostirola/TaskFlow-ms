package com.example.logservice.rabbitmq;

import com.example.logservice.model.Log;
import com.example.logservice.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class LogConsumer {
    private final LogService logService;
    private final ObjectMapper mapper;
    @RabbitListener(queues = {"log-queue"})
    public void receive(@Payload String message) {
        try {
            logService.registrarLog(mapper.readValue(message, Log.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
