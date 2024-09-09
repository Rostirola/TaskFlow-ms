package com.example.tarefaservice;

import com.example.tarefaservice.model.Categoria;
import com.example.tarefaservice.model.Tarefa;
import com.example.tarefaservice.service.TarefaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TarefaServiceTest {

    @Autowired
    TarefaService tarefaService;

    @Test
    @DisplayName("Deve retornar todas as tarefas.")
    public void getAll() {
        List<Tarefa> all = tarefaService.getAll();
        assertEquals(1, all.size());
    }

    @Test
    @DisplayName("Deve retornar todas as tarefas por id.")
    public void findById() {
        Optional<Tarefa> tarefa = tarefaService.findById(1L);
        assertTrue(tarefa.isPresent());
    }

    @Test
    @DisplayName("Deve retornar todas as tarefas por nome.")
    public void filterByName() {
        List<Tarefa> all = tarefaService.filterByName("TP1");
        assertEquals(1, all.size());
    }

    @Test
    @DisplayName("Deve deletar a tarefa por id.")
    public void deleteById() {
        tarefaService.deleteById(1L);
        Optional<Tarefa> tarefa = tarefaService.findById(1L);
        assertFalse(tarefa.isPresent());
    }

    @Test
    @DisplayName("Deve criar um novo registro.")
    public void save() {
        List<Tarefa> all = tarefaService.getAll();
        int estadoInicial = all.size();
        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Tp 02");
        tarefa.setDescricao("Desenvolver uma Camada de Persistência Real");
        tarefa.setCategoria(Categoria.AT);
        tarefa.setProjeto(1L);
        tarefa.setDataCadastro("2023-09-05T15:30:00");
        tarefa.setDataUltimaAlteracao("2023-09-05T15:30:00");
        tarefaService.save(tarefa);
        all = tarefaService.getAll();
        int estadoFinal = all.size();
        assertEquals(estadoInicial + 1, estadoFinal);
    }

    @Test
    @DisplayName("Deve atualizar um registro.")
    public void update() {
        Optional<Tarefa> tarefa = tarefaService.findById(1L);
        Tarefa tarefaAtt = new Tarefa();
        tarefaAtt.setNome("TP1");
        tarefaAtt.setDescricao("1");
        tarefaAtt.setCategoria(Categoria.TP2);
        tarefaAtt.setProjeto(1L);
        tarefaAtt.setDataCadastro("2023-09-05T15:30:00");
        tarefaAtt.setDataUltimaAlteracao("2023-09-05T15:30:00");
        tarefaService.update(1L, tarefaAtt);
        Optional<Tarefa> tarefaAtualizado = tarefaService.findById(1L);
        assertEquals(tarefa, tarefaAtualizado);
    }
}
