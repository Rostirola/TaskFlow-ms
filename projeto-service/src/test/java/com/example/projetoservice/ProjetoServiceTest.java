package com.example.projetoservice;

import com.example.projetoservice.model.Categoria;
import com.example.projetoservice.model.Projeto;
import com.example.projetoservice.service.ProjetoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjetoServiceTest {

    @Autowired
    ProjetoService projetoService;

    @Test
    @DisplayName("Deve retornar todos os projetos.")
    public void getAll() {
        List<Projeto> all = projetoService.getAll();
        assertEquals(4, all.size());
    }

    @Test
    @DisplayName("Deve retornar por id.")
    public void findById() {
        Optional<Projeto> projeto = projetoService.findById(1L);
        assertTrue(projeto.isPresent());
    }

    @Test
    @DisplayName("Deve retornar por nome.")
    public void filterByName() {
        List<Projeto> projetos = projetoService.filterByName("TP2");
        assertEquals(1, projetos.size());
    }

    @Test
    @DisplayName("Deve deletar por id.")
    public void deleteById() {
        projetoService.deleteById(1L);
        Optional<Projeto> projeto = projetoService.findById(1L);
        assertFalse(projeto.isPresent());
    }

    @Test
    @DisplayName("Deve criar um novo registro.")
    public void save() {
        List<Projeto> all = projetoService.getAll();
        int estadoInicial = all.size();
        Projeto projeto = new Projeto();
        projeto.setNome("TP5");
        projeto.setDescricao("Desenvolver uma Camada de Persistência Real");
        projeto.setCategoria(Categoria.DR4);
        projeto.setDataCadastro(LocalDateTime.parse("2023-09-05T15:30:00"));
        projeto.setDataUltimaAlteracao(LocalDateTime.parse("2023-09-05T15:30:00"));
        projetoService.save(projeto);
        all = projetoService.getAll();
        int estadoFinal = all.size();
        assertEquals(estadoInicial + 1,estadoFinal);
    }

    @Test
    @DisplayName("Deve atualizar um registro.")
    public void update() {
        Optional<Projeto> projeto = projetoService.findById(1L);
        Projeto projetoAtt = new Projeto();
        projetoAtt.setNome("Tp 02");
        projetoAtt.setDescricao("Desenvolver uma Camada de Persistência Real");
        projetoAtt.setCategoria(Categoria.DR4);
        projetoAtt.setDataCadastro(LocalDateTime.parse("2023-09-05T15:30:00"));
        projetoAtt.setDataUltimaAlteracao(LocalDateTime.parse("2023-09-05T15:30:00"));
        projetoService.update(4L, projetoAtt);
        Optional<Projeto> projetoAtualizado = projetoService.findById(1L);
        assertEquals(projeto, projetoAtualizado);
    }
}
