package br.edu.ifsp.kanban.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pagina")
public class Pagina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagina")
    private Integer idPagina;

    @Column(name = "nome")
    private String nome;

    // Relacionamento com Bloco
    @OneToMany(mappedBy = "pagina")
    private Set<Bloco> blocos;

    // Relacionamento com PaginaUsuario
    @OneToMany(mappedBy = "pagina")
    private Set<PaginaUsuario> usuarios;
}
