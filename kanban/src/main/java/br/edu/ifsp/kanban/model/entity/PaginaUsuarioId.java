package br.edu.ifsp.kanban.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PaginaUsuarioId implements Serializable {
    @Column(name = "id_pagina")
    private Integer idPagina;
    @Column(name = "id_usuario")
    private Integer idUsuario;

/*
    // Construtores para a chave composta
    public PaginaUsuarioId() {}

    public PaginaUsuarioId(Integer idPagina, Integer idUsuario) {
        this.idPagina = idPagina;
        this.idUsuario = idUsuario;
    }

    // Metodos para garantir a integridade referencial da chave composta
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaginaUsuarioId that = (PaginaUsuarioId) o;
        return Objects.equals(idPagina, that.idPagina) && Objects.equals(idUsuario, that.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPagina, idUsuario);
    }

    // Getters e Setters

    public Integer getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(Integer idPagina) {
        this.idPagina = idPagina;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

 */
}
