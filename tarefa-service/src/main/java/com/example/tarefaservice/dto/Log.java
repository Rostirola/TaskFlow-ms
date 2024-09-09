package com.example.tarefaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private String acao;
    private String descricao;
    private LocalDateTime dataHora;
    private Map<String, Object> datalhes;
}
