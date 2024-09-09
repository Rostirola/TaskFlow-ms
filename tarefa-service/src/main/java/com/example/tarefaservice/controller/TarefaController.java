package com.example.tarefaservice.controller;

import com.example.tarefaservice.exception.ResourceNotFoundException;
import com.example.tarefaservice.model.Tarefa;
import com.example.tarefaservice.payload.MessagePayload;
import com.example.tarefaservice.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class TarefaController {
    final TarefaService tarefaService;

    @Operation(summary = "Recupera todas as tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Recuperadas com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Ocorreu um Erro",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            )
    })
    @GetMapping
    public ResponseEntity<List<Tarefa>> getAll(@RequestParam(required = false) Optional<String> nome) {
        if (nome.isEmpty()) {
            return ResponseEntity.ok(tarefaService.getAll());
        } else {
            List<Tarefa> tarefas = tarefaService.filterByName(nome.get());
            if (tarefas.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(tarefas);
            }
        }
    }

    @Operation(summary = "Recupera uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Recuperada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Ocorreu um Erro",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Optional<Tarefa> localizado = tarefaService.findById(id);
            return ResponseEntity.ok(localizado);
        } catch (ResourceNotFoundException ex) {
            Map<String, String> message = Map.of("Message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @Operation(summary = "Salva uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa Salva",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tarefa.class))}),
            @ApiResponse(responseCode = "404", description = "Ocorreu um Erro",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            )
    })
    @PostMapping
    public ResponseEntity<MessagePayload> save(@RequestBody Tarefa tarefa) {
        tarefaService.save(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessagePayload("Criada com sucesso"));

    }

    @Operation(summary = "Atualizando uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Atualizada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Ocorreu um Erro",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessagePayload> update(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        try {
            tarefaService.update(id, tarefa);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Atualizada com sucesso"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }

    }

    @Operation(summary = "Deleta uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Deletada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Ocorreu um Erro",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessagePayload> delete(@PathVariable Long id) {
        try {
            tarefaService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Deletada com sucesso"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }
}
