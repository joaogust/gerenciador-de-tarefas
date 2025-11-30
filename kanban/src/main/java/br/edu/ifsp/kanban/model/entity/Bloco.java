package br.edu.ifsp.kanban.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bloco")
public class Bloco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bloco")
    private Integer idBloco;

    @Column(name = "nome")
    private String nome;
    @Column(name = "estado")
    private String estado;

    // Relacionamento com Pagina
    @Column(name = "id_pagina")
    private Integer idPagina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pagina", insertable = false, updatable = false)
    private Pagina pagina;

    // Relacionamento com Tarefa
    @OneToMany(mappedBy = "bloco")
    private Set<Tarefa> tarefas;
}
