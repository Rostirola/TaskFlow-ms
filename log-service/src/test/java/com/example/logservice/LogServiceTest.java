package com.example.logservice;

import com.example.logservice.model.Log;
import com.example.logservice.service.LogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LogServiceTest {

    @Autowired
    LogService logService;

    @Test
    @DisplayName("Deve verificar os logs, adicionar um log, e virificar novamente.")
    public void logTest() {
        assertEquals(1, logService.obterTodosLogs().size());
        Log log = new Log();
        log.setId(2L);
        log.setAcao("Teste log");
        log.setDescricao("Testeando logs via testes automatizados");
        log.setDataHora(LocalDateTime.now());
        logService.registrarLog(log);
        assertEquals(2, logService.obterTodosLogs().size());
    }
}
