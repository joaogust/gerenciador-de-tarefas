package br.edu.ifsp.kanban.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pagina_usuario")
public class PaginaUsuario {
    // Para utilizar a pk composta
    @EmbeddedId
    private PaginaUsuarioId idPaginaUsuario;

    @Column(name = "papel")
    private String papel;

    @Column(name = "id_pagina", insertable = false, updatable = false)
    private Long idPagina;

    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Long idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPagina")
    @JoinColumn(name = "id_pagina", insertable = false, updatable = false)
    private Pagina pagina;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;
/*
    // Contrutores para a chave composta
    public PaginaUsuario() {}

    public PaginaUsuario(Pagina pagina, Usuario usuario, String papel) {
        this.pagina = pagina;
        this.usuario = usuario;
        this.papel = papel;
        this.idPaginaUsuario = new PaginaUsuarioId(pagina.getIdPagina(), usuario.getIdUsuario());
    }

    public PaginaUsuarioId getIdPaginaUsuario() {
        return idPaginaUsuario;
    }

    public void setIdPaginaUsuario(PaginaUsuarioId idPaginaUsuario) {
        this.idPaginaUsuario = idPaginaUsuario;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

 */
}
