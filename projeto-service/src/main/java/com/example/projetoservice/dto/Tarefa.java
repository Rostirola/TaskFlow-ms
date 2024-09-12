package com.example.projetoservice.dto;

import com.example.projetoservice.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {
    private String nome;
    private String descricao;
    private String categoria;
    private long projeto;
    private String dataCadastro;
    private String dataUltimaAlteracao;
}
