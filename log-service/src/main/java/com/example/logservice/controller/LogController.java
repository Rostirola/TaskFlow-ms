package com.example.logservice.controller;

import com.example.logservice.model.Log;
import com.example.logservice.payload.MessagePayload;
import com.example.logservice.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class LogController {

    private final LogService logService;

    @Operation(summary = "Salva um log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Log salvo com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Ocorreu um Erro",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            )
    })
    @PostMapping
    public void registrarLog(@RequestBody Log log) {
        logService.registrarLog(log);
    }

    @Operation(summary = "Recupera todos os logs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Logs recuperados com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Ocorreu um Erro",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            )
    })
    @GetMapping
    public List<Log> obterTodosLogs() {
        return logService.obterTodosLogs();
    }
}
