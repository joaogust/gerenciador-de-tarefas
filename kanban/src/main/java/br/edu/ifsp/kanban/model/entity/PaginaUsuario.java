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
}
