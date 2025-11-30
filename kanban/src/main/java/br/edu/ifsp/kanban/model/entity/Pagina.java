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
    private List<Bloco> blocos = new ArrayList<>();

    // Relacionamento com PaginaUsuario
    @OneToMany(mappedBy = "pagina")
    private Set<PaginaUsuario> usuarios = new HashSet<>();
/*
    // Construtor vazio
    public Pagina() {}

    // Getters e Setters

    public Integer getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(Integer idPagina) {
        this.idPagina = idPagina;
    }

    public Set<PaginaUsuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<PaginaUsuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Bloco> getBlocos() {
        return blocos;
    }

    public void setBlocos(List<Bloco> blocos) {
        this.blocos = blocos;
    }

 */
}
