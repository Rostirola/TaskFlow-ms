package com.example.tarefaservice.rabbitmq;

import com.example.tarefaservice.dto.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TarefaProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public void send(Log log) throws JsonProcessingException {
        amqpTemplate.convertAndSend("log-exc", "log-rk", objectMapper.writeValueAsString(log));
    }
}
