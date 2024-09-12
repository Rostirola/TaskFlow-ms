package com.example.tarefaservice.service;

import com.example.tarefaservice.dto.Log;
import com.example.tarefaservice.model.Tarefa;
import com.example.tarefaservice.rabbitmq.TarefaProducer;
import com.example.tarefaservice.repository.TarefaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final TarefaProducer producer;

    public List<Tarefa> getAll() {
        return tarefaRepository.findAll();
    };

    public Optional<Tarefa> findById(Long id) {
        return tarefaRepository.findById(id);
    };

    public List<Tarefa> filterByName(String nome) {
        System.out.println(nome);
        System.out.println(tarefaRepository.findAll());
        System.out.println(tarefaRepository.findAll().stream());
        return tarefaRepository.findAll().stream().filter(tarefa -> tarefa.getNome().startsWith(nome)).toList();
    };

    public void deleteById(Long id) throws JsonProcessingException {
        tarefaRepository.deleteById(id);
        Map<String, Object> details = new HashMap<>();
        details.put("tarefa_id", id);

        producer.send(new Log(
                "DELETA_TAREFA",
                "Uma tarefa foi deletada com sucesso.",
                LocalDateTime.now(),
                details
        ));
    };

    public void save(Tarefa tarefa) throws JsonProcessingException {
        tarefaRepository.save(tarefa);
        Map<String, Object> details = new HashMap<>();
        details.put("tarefa_id", tarefa.getId());
        details.put("tarefa_nome", tarefa.getNome());
        details.put("tarefa_descricao", tarefa.getDescricao());
        details.put("tarefa_categoria", tarefa.getCategoria());
        details.put("projeto_id", tarefa.getProjeto());
        details.put("tarefa_data_cadastro", tarefa.getDataCadastro());
        details.put("tarefa_data_utima_alteracao", tarefa.getDataUltimaAlteracao());

        producer.send(new Log(
                "CRIA_TAREFA",
                "Uma nova tarefa foi criada",
                LocalDateTime.now(),
                details
        ));
    };

    public Tarefa update(Long id, Tarefa atualizada) throws JsonProcessingException {
        atualizada.setId(id);
        Map<String, Object> details = new HashMap<>();
        details.put("tarefa_id", id);
        details.put("tarefa_nome", atualizada.getNome());
        details.put("tarefa_descricao", atualizada.getDescricao());
        details.put("tarefa_categoria", atualizada.getCategoria());
        details.put("projeto_id", atualizada.getProjeto());
        details.put("tarefa_data_cadastro", atualizada.getDataCadastro());
        details.put("tarefa_data_utima_alteracao", atualizada.getDataUltimaAlteracao());

        producer.send(new Log(
                "ATUALIZA_PROJETO",
                "Uma tarefa foi alterada com sucesso.",
                LocalDateTime.now(),
                details
        ));
        return tarefaRepository.save(atualizada);
    };
}
