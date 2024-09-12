package com.example.projetoservice.service;

import com.example.projetoservice.dto.Log;
import com.example.projetoservice.dto.ProjetoComTarefas;
import com.example.projetoservice.dto.Tarefa;
import com.example.projetoservice.model.Projeto;
import com.example.projetoservice.rabbitmq.ProjetoProducer;
import com.example.projetoservice.repository.ProjetoRepository;
import com.example.projetoservice.service.feign.TarefaClient;
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
public class ProjetoService {
    private final ProjetoRepository projetoRepository;
    private final ProjetoProducer producer;
    private final TarefaClient tarefaClient;

    public List<ProjetoComTarefas> getAll() {
        List<Tarefa> todasTarefas = tarefaClient.getTarefas();
        List<Projeto> projetos = projetoRepository.findAll();

        return projetos.stream().map(projeto -> {
            List<Tarefa> tarefasDoProjeto = todasTarefas.stream()
                    .filter(tarefa -> tarefa.getProjeto() == projeto.getId())
                    .toList();

            return new ProjetoComTarefas(projeto, tarefasDoProjeto);
        }).toList();
    };

    public Optional<ProjetoComTarefas> findById(Long id) {
        List<Tarefa> todasTarefas = tarefaClient.getTarefas();
        Optional<Projeto> projetoOptional = projetoRepository.findById(id);

        return projetoOptional.map(projeto -> {
            List<Tarefa> tarefasDoProjeto = todasTarefas.stream()
                    .filter(tarefa -> tarefa.getProjeto() == projeto.getId())
                    .toList();

            return new ProjetoComTarefas(projeto, tarefasDoProjeto);
        });
    }


    public List<ProjetoComTarefas> filterByName(String nome) {
        List<Tarefa> todasTarefas = tarefaClient.getTarefas();
        List<Projeto> projetos =  projetoRepository.findAll().stream().filter(projeto -> projeto.getNome().startsWith(nome)).toList();

        return projetos.stream().map(projeto -> {
            List<Tarefa> tarefasDoProjeto = todasTarefas.stream()
                    .filter(tarefa -> tarefa.getProjeto() == projeto.getId())
                    .toList();

            return new ProjetoComTarefas(projeto, tarefasDoProjeto);
        }).toList();
    };

    public void deleteById(Long id) throws JsonProcessingException {
        projetoRepository.deleteById(id);
        Map<String, Object> details = new HashMap<>();
        details.put("projeto_id", id);

        producer.send(new Log(
                "DELETA_PROJETO",
                "Um projeto foi deletado.",
                LocalDateTime.now(),
                details
        ));
    };

    public void save(Projeto projeto) throws JsonProcessingException {
        projetoRepository.save(projeto);
        Map<String, Object> details = new HashMap<>();
        details.put("projeto_id", projeto.getId());
        details.put("projeto_nome", projeto.getNome());
        details.put("projeto_descricao", projeto.getDescricao());
        details.put("projeto_categoria", projeto.getCategoria());
        details.put("projeto_data_cadastro", projeto.getDataCadastro());
        details.put("projeto_data_utima_alteracao", projeto.getDataUltimaAlteracao());

        producer.send(new Log(
                "CRIA_PROJETO",
                "Um novo projeto foi criado",
                LocalDateTime.now(),
                details
        ));
    };

    public Projeto update(Long id, Projeto atualizado) throws JsonProcessingException {
        atualizado.setId(id);
        Map<String, Object> details = new HashMap<>();
        details.put("projeto_id", id);
        details.put("projeto_nome", atualizado.getNome());
        details.put("projeto_descricao", atualizado.getDescricao());
        details.put("projeto_categoria", atualizado.getCategoria());
        details.put("projeto_data_cadastro", atualizado.getDataCadastro());
        details.put("projeto_data_utima_alteracao", atualizado.getDataUltimaAlteracao());

        producer.send(new Log(
                "ATUALIZA_PROJETO",
                "Um projeto foi alterado.",
                LocalDateTime.now(),
                details
        ));
        return projetoRepository.save(atualizado);
    };
}
