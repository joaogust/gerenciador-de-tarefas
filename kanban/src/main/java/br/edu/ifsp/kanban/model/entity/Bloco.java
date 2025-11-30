package br.edu.ifsp.kanban.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private List<Tarefa> tarefas = new ArrayList<>();
/*
    // Construtor vazio
    public Bloco() {}

    // Getters e Setters

    public Integer getIdBloco() {
        return idBloco;
    }

    public void setIdBloco(Integer idBloco) {
        this.idBloco = idBloco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(Integer idPagina) {
        this.idPagina = idPagina;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

 */
}
