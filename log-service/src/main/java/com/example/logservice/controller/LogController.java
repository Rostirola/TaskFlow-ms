package com.example.logservice.controller;

import com.example.logservice.model.Log;
import com.example.logservice.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class LogController {

    private LogService logService;

    @PostMapping
    public void registrarLog(@RequestBody Log log) {
        logService.registrarLog(log);
    }

    @GetMapping
    public List<Log> obterTodosLogs() {
        return logService.obterTodosLogs();
    }
}
