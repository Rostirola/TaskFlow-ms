package com.example.tarefaservice.service;

import com.example.tarefaservice.model.Tarefa;
import com.example.tarefaservice.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaRepository tarefaRepository;

    public List<Tarefa> getAll() {
        return tarefaRepository.findAll();
    };

    public Optional<Tarefa> findById(Long id) {
        return tarefaRepository.findById(id);
    };

    public List<Tarefa> filterByName(String nome) {
        return tarefaRepository.findAll().stream().filter(projeto -> projeto.getNome().startsWith(nome)).toList();
    };

    public void deleteById(Long id) {
        tarefaRepository.deleteById(id);
    };

    public void save(Tarefa tarefa) {
        tarefaRepository.save(tarefa);
    };

    public Tarefa update(Long id, Tarefa atualizada) {
        atualizada.setId(id);
        return tarefaRepository.save(atualizada);
    };
}
