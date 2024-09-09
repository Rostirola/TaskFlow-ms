package com.example.projetoservice.service;

import com.example.projetoservice.dto.Log;
import com.example.projetoservice.model.Projeto;
import com.example.projetoservice.repository.ProjetoRepository;
import com.example.projetoservice.service.feign.LogClient;
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
    private final LogClient logClient;

    public List<Projeto> getAll() {
        return projetoRepository.findAll();
    };

    public Optional<Projeto> findById(Long id) {
        return projetoRepository.findById(id);
    };

    public List<Projeto> filterByName(String nome) {
        return projetoRepository.findAll().stream().filter(projeto -> projeto.getNome().startsWith(nome)).toList();
    };

    public void deleteById(Long id) {
        projetoRepository.deleteById(id);
        Map<String, Object> details = new HashMap<>();
        details.put("projeto_id", id);

        logClient.registrarLog(new Log(
                "DELETA_PROJETO",
                "Um projeto foi deletado.",
                LocalDateTime.now(),
                details
        ));
    };

    public void save(Projeto projeto) {
        projetoRepository.save(projeto);
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
    };

    public Projeto update(Long id, Projeto atualizado) {
        atualizado.setId(id);
        Map<String, Object> details = new HashMap<>();
        details.put("projeto_id", id);
        details.put("projeto_nome", atualizado.getNome());
        details.put("projeto_descricao", atualizado.getDescricao());
        details.put("projeto_categoria", atualizado.getCategoria());
        details.put("projeto_data_cadastro", atualizado.getDataCadastro());
        details.put("projeto_data_utima_alteracao", atualizado.getDataUltimaAlteracao());

        logClient.registrarLog(new Log(
                "ATUALIZA_PROJETO",
                "Um projeto foi alterado.",
                LocalDateTime.now(),
                details
        ));
        return projetoRepository.save(atualizado);
    };
}
