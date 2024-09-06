package com.example.logservice.service;


import com.example.logservice.model.Log;
import com.example.logservice.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public void registrarLog(Log projeto) {
        logRepository.save(projeto);
    };

    public List<Log> obterTodosLogs() {
        return logRepository.findAll();
    };
}
