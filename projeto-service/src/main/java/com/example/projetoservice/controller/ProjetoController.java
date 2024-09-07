package com.example.projetoservice.controller;

import com.example.projetoservice.dto.Log;
import com.example.projetoservice.exception.ResourceNotFoundException;
import com.example.projetoservice.model.Projeto;
import com.example.projetoservice.payload.MessagePayload;
import com.example.projetoservice.service.ProjetoService;
import com.example.projetoservice.service.feign.LogClient;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class ProjetoController {

    private final ProjetoService projetoService;

    private final LogClient logClient;

    @GetMapping
    public ResponseEntity<List<Projeto>> getAll(@RequestParam(required = false) Optional<String> nome){
        if(nome.isEmpty()){
            return ResponseEntity.ok(projetoService.getAll());
        }else {
            List<Projeto> projetos = projetoService.filterByName(nome.get());
            if(projetos.isEmpty()){
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok(projetos);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            Optional<Projeto> localizado = projetoService.findById(id);
            return ResponseEntity.ok(localizado);
        }catch (ResourceNotFoundException ex){
            Map<String, String> message = Map.of("Message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @Operation(summary = "Salva um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Projeto Salvo",
                    content ={@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Projeto.class))} )
    })
    @PostMapping
    public ResponseEntity<MessagePayload> save(@RequestBody Projeto projeto){
        projetoService.save(projeto);

        Map<String, Object> details = new HashMap<>();
        details.put("projeto_id", projeto.getId());
        details.put("projeto_nome", projeto.getNome());
        details.put("projeto_descricao", projeto.getDescricao());
        details.put("projeto_categoria", projeto.getCategoria());
        details.put("projeto_data_cadastro", projeto.getDataCadastro());
        details.put("projeto_data_utima_alteracao", projeto.getDataUltimaAlteracao());

        logClient.registrarLog(new Log(
                "CRIA_PROJETO",
                "Um novo projeto foi criado",
                LocalDateTime.now(),
                details
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessagePayload("Criado com sucesso"));

    }

    @Operation(summary = "Atualizando um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Atualizado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Ocorreu um Erro",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessagePayload> update(@PathVariable Long id, @RequestBody Projeto projeto){
        try {
            projetoService.update(id,projeto);

            Map<String, Object> details = new HashMap<>();
            details.put("projeto_id", id);
            details.put("projeto_nome", projeto.getNome());
            details.put("projeto_descricao", projeto.getDescricao());
            details.put("projeto_categoria", projeto.getCategoria());
            details.put("projeto_data_cadastro", projeto.getDataCadastro());
            details.put("projeto_data_utima_alteracao", projeto.getDataUltimaAlteracao());

            logClient.registrarLog(new Log(
                    "ATUALIZA_PROJETO",
                    "Um projeto foi alterado.",
                    LocalDateTime.now(),
                    details
            ));

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Atualizado com sucesso"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }

    }

    @Operation(summary = "Deleta um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Deletado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Ocorreu um Erro",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class))}
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessagePayload> delete(@PathVariable Long id){
        try {
            projetoService.deleteById(id);

            Map<String, Object> details = new HashMap<>();
            details.put("projeto_id", id);

            logClient.registrarLog(new Log(
                    "DELETA_PROJETO",
                    "Um projeto foi deletado.",
                    LocalDateTime.now(),
                    details
            ));

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Deletado com sucesso"));
        }catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }
}
