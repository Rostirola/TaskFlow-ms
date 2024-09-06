package com.example.tarefaservice.service.feign;

import com.example.tarefaservice.model.Projeto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PROJETO-SERVICE")
public interface ProjetoClient {
    @GetMapping("/{id}")
    Projeto obterProjeto(@PathVariable("id") Long id);
}
