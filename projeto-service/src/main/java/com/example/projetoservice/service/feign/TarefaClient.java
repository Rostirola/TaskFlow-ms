package com.example.projetoservice.service.feign;

import com.example.projetoservice.dto.Tarefa;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("TAREFA-SERVICE")
public interface TarefaClient {
    @GetMapping("/")
    List<Tarefa> getTarefas();
}