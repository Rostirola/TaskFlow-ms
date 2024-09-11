package com.example.projetoservice.rabbitmq;

import com.example.projetoservice.dto.Log;
import com.example.projetoservice.model.Projeto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjetoProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public void send(Log log) throws JsonProcessingException {
        amqpTemplate.convertAndSend("log-exc", "log-rk", objectMapper.writeValueAsString(log));
    }
}
