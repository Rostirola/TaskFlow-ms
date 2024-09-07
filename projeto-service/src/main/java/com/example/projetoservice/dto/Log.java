package com.example.projetoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private String action;
    private String description;
    private LocalDateTime timestamp;
    private Map<String, Object> details;
}
