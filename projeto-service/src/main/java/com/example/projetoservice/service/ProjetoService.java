package com.example.projetoservice.service;

import com.example.projetoservice.model.Projeto;
import com.example.projetoservice.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjetoService {
    private final ProjetoRepository projetoRepository;

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
    };

    public void save(Projeto projeto) {
        projetoRepository.save(projeto);
    };

    public Projeto update(Long id, Projeto atualizado) {
        atualizado.setId(id);
        return projetoRepository.save(atualizado);
    };
}
