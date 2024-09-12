package com.example.projetoservice.dto;

import com.example.projetoservice.model.Projeto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjetoComTarefas {
    private Projeto projeto;
    private List<Tarefa> tarefas;
}